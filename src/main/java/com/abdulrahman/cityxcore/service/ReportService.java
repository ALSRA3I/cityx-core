package com.abdulrahman.cityxcore.service;

import com.abdulrahman.cityxcore.model.Case;
import com.abdulrahman.cityxcore.model.CrimeReport;
import com.abdulrahman.cityxcore.model.Evidence;
import com.abdulrahman.cityxcore.model.EvidenceType;
import com.abdulrahman.cityxcore.repository.CaseRepository;
import com.abdulrahman.cityxcore.repository.CrimeReportRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.IOUtils;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ReportService {

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private CrimeReportRepository crimeReportRepository;

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    /**
     * Generates a PDF report for a given case ID.
     * The report includes case details, all evidence (images and text), suspects, victims, and witnesses.
     */
    public byte[] generateCaseReportPDF(Long caseId) throws Exception {
        Case aCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found with id: " + caseId));

        // Create ByteArrayOutputStream to write PDF
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        // Title with Font
        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        Font titleFont = new Font(baseFont, 18);
        document.add(new Paragraph("Case Report", titleFont));

        // Case Details
        document.add(new Paragraph("Case Number: " + aCase.getCaseNumber()));
        document.add(new Paragraph("Case Name: " + aCase.getCaseName()));
        document.add(new Paragraph("Description: " + aCase.getDescription()));
        document.add(new Paragraph("Area/City: " + aCase.getArea()));
        document.add(new Paragraph("Case Type: " + aCase.getCaseType()));
        document.add(new Paragraph("Case Level: " + aCase.getCaseLevel()));
        document.add(new Paragraph("Authorization Level: " + aCase.getAuthorizationLevel()));
        document.add(new Paragraph("Created At: " + aCase.getCreatedAt()));
        document.add(new Paragraph("\n"));

        // Evidence Section
        document.add(new Paragraph("Evidence:"));
        List<Evidence> evidences = aCase.getEvidences();
        if (evidences.isEmpty()) {
            document.add(new Paragraph("No evidence available."));
        } else {
            for (Evidence ev : evidences) {
                document.add(new Paragraph("Evidence ID: " + ev.getId()));
                document.add(new Paragraph("Remarks: " + ev.getRemarks()));
                if (ev.getEvidenceType() == EvidenceType.TEXT) {
                    document.add(new Paragraph("Content: " + ev.getContent()));
                } else if (ev.getEvidenceType() == EvidenceType.IMAGE) {
                    // Retrieve image bytes from MinIO.
                    // Assuming fileUrl is of format "http://minio-server/{bucketName}/{fileName}"
                    String fileUrl = ev.getFileUrl();
                    String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                    try (InputStream is = minioClient.getObject(
                            GetObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(fileName)
                                    .build())) {
                        byte[] imageBytes = IOUtils.toByteArray(is);
                        Image image = Image.getInstance(imageBytes);
                        image.scaleToFit(400, 300);
                        document.add(image);
                    }
                }
                document.add(new Paragraph("\n"));
            }
        }

        // Suspects Section
        document.add(new Paragraph("Suspects:"));
        if (aCase.getSuspects().isEmpty()) {
            document.add(new Paragraph("No suspects available."));
        } else {
            aCase.getSuspects().forEach(suspect ->
                    {
                        try {
                            document.add(new Paragraph("Suspect: " + suspect.getName()));
                        } catch (DocumentException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        }
        document.add(new Paragraph("\n"));

        // Victims Section
        document.add(new Paragraph("Victims:"));
        if (aCase.getVictims().isEmpty()) {
            document.add(new Paragraph("No victims available."));
        } else {
            aCase.getVictims().forEach(victim ->
                    {
                        try {
                            document.add(new Paragraph("Victim: " + victim.getName()));
                        } catch (DocumentException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        }
        document.add(new Paragraph("\n"));

        // Witnesses Section
        document.add(new Paragraph("Witnesses:"));
        if (aCase.getWitnesses().isEmpty()) {
            document.add(new Paragraph("No witnesses available."));
        } else {
            aCase.getWitnesses().forEach(witness ->
                    {
                        try {
                            document.add(new Paragraph("Witness: " + witness.getName()));
                        } catch (DocumentException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        }
        document.add(new Paragraph("\n"));

        // Finalize document
        document.close();
        writer.close();

        return baos.toByteArray();
    }

    /**
     * Returns the status of the case given the report id.
     * For simplicity, this method finds the crime report by reportId and returns its status.
     */
    public String getCaseStatusByReportId(String reportId) {
        CrimeReport report = crimeReportRepository.findByReportId(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + reportId));
        return report.getStatus();
    }
}
