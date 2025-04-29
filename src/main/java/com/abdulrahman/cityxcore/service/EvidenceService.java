package com.abdulrahman.cityxcore.service;

import com.abdulrahman.cityxcore.dto.EvidenceDTO;
import com.abdulrahman.cityxcore.dto.EvidenceDetailDTO;
import com.abdulrahman.cityxcore.dto.EvidenceImageDTO;
import com.abdulrahman.cityxcore.model.AuditLog;
import com.abdulrahman.cityxcore.model.Case;
import com.abdulrahman.cityxcore.model.Evidence;
import com.abdulrahman.cityxcore.model.EvidenceType;
import com.abdulrahman.cityxcore.repository.CaseRepository;
import com.abdulrahman.cityxcore.repository.EvidenceRepository;
import com.abdulrahman.cityxcore.repository.AuditLogRepository;
import io.minio.*;
import jakarta.transaction.Transactional;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class EvidenceService {

    @Autowired
    private EvidenceRepository evidenceRepository;

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    // Inject the MinioClient (ensure you have the MinIO dependency and configuration)
    @Autowired
    private MinioClient minioClient;

    // The bucket name configured for storing evidence images.
    @Value("${minio.bucket-name}")
    private String bucketName;

    // MinIO server URL for constructing file URL
    @Value("${minio.url}")
    private String minioUrl;


    @Transactional
    public EvidenceDTO recordEvidence(Long caseId, EvidenceType evidenceType, String content, MultipartFile file, String remarks) throws Exception {
        Case aCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found with id: " + caseId));

        String textContent = null;
        String fileUrl = null;

        if (evidenceType == EvidenceType.TEXT) {
            if (content == null || content.trim().isEmpty()) {
                throw new RuntimeException("Text content must be provided for text evidence.");
            }
            textContent = content;
        } else if (evidenceType == EvidenceType.IMAGE) {
            if (file == null || file.isEmpty()) {
                throw new RuntimeException("Image file must be provided for image evidence.");
            }
            // Validate that the uploaded file is an image.
            if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
                throw new RuntimeException("Uploaded file is not an image.");
            }
            // Generate a unique file name.
            String fileName = "evidence-" + System.currentTimeMillis() + "-" + file.getOriginalFilename();

            // Ensure the bucket exists
            BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
            boolean bucketExists = minioClient.bucketExists(bucketExistsArgs);

            if (!bucketExists) {
                MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();
                minioClient.makeBucket(makeBucketArgs);
            }

            // Upload the image to MinIO.
            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(fileName)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            }

            // Construct the URL to access the file (adjust according to your MinIO setup).
            // The URL format depends on your MinIO setup (e.g., whether it's exposed via a reverse proxy).
            fileUrl = minioUrl + "/" + bucketName + "/" + fileName;
        }

        // Create an Evidence entity and save it to the repository
        Evidence evidence = new Evidence();
        evidence.setEvidenceType(evidenceType);
        evidence.setRemarks(remarks);
        evidence.setACase(aCase);

        if (evidenceType == EvidenceType.TEXT) {
            evidence.setContent(textContent);
        } else if (evidenceType == EvidenceType.IMAGE) {
            evidence.setFileUrl(fileUrl);
            evidence.setContent(null);
        }

        Evidence savedEvidence = evidenceRepository.save(evidence);

        // Return a DTO with the saved data
        return new EvidenceDTO(savedEvidence.getId(), savedEvidence.getEvidenceType(),
                evidenceType == EvidenceType.IMAGE ? savedEvidence.getFileUrl() : savedEvidence.getContent(),
                savedEvidence.getRemarks());
    }

    public EvidenceDetailDTO getEvidenceDetail(Long evidenceId) throws Exception {
        Evidence evidence = evidenceRepository.findById(evidenceId)
                .orElseThrow(() -> new RuntimeException("Evidence not found with id: " + evidenceId));
        Long imageSize = null;
        String content = null;
        if (evidence.getEvidenceType() == EvidenceType.IMAGE) {
            // Extract file name from the stored fileUrl.
            // Assuming fileUrl is of the format "http://minio-server/mybucket/filename"
            String fileUrl = evidence.getFileUrl();
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            StatObjectResponse stat = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build());
            imageSize = stat.size();
            // For image evidence, we return the fileUrl as content.
            content = fileUrl;
        } else {
            // For text evidence, content holds the text.
            content = evidence.getContent();
        }
        return new EvidenceDetailDTO(evidence.getId(), evidence.getEvidenceType(), content,
                evidence.getRemarks(), imageSize);
    }

    /**
     * Retrieves the image associated with an evidence entry.
     * Throws an exception if the evidence is not an image.
     */
    public EvidenceImageDTO getEvidenceImage(Long evidenceId) throws Exception {
        Evidence evidence = evidenceRepository.findById(evidenceId)
                .orElseThrow(() -> new RuntimeException("Evidence not found with id: " + evidenceId));
        if (evidence.getEvidenceType() != EvidenceType.IMAGE) {
            throw new RuntimeException("Evidence is not an image.");
        }
        // Extract file name from fileUrl.
        String fileUrl = evidence.getFileUrl();
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

        // Retrieve file's metadata for content type.
        StatObjectResponse stat = minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build());
        String contentType = stat.contentType();

        // Retrieve the file data from MinIO.
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build())) {
            byte[] data = IOUtils.toByteArray(stream);
            return new EvidenceImageDTO(data, contentType);
        }
    }

    /**
     * Updates an evidence entry.
     * The evidence type cannot be changed.
     * For TEXT evidence, the content can be updated.
     * For IMAGE evidence, optionally a new image file can be provided to replace the existing one.
     */
    @Transactional
    public EvidenceDTO updateEvidence(Long evidenceId, String newContent, MultipartFile newFile, String newRemarks) throws Exception {
        Evidence evidence = evidenceRepository.findById(evidenceId)
                .orElseThrow(() -> new RuntimeException("Evidence not found with id: " + evidenceId));

        // Ensure that the evidence type remains unchanged.
        if (evidence.getEvidenceType() == EvidenceType.TEXT) {
            if (newContent != null && !newContent.trim().isEmpty()) {
                evidence.setContent(newContent);
            }
        } else if (evidence.getEvidenceType() == EvidenceType.IMAGE) {
            if (newFile != null && !newFile.isEmpty()) {
                // Validate that the uploaded file is an image.
                if (newFile.getContentType() == null || !newFile.getContentType().startsWith("image/")) {
                    throw new RuntimeException("Uploaded file is not an image.");
                }
                // Generate a new file name.
                String fileName = "evidence-" + System.currentTimeMillis() + "-" + newFile.getOriginalFilename();
                try (InputStream inputStream = newFile.getInputStream()) {
                    minioClient.putObject(
                            PutObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(fileName)
                                    .stream(inputStream, newFile.getSize(), -1)
                                    .contentType(newFile.getContentType())
                                    .build()
                    );
                }
                // Update the file URL.
                String fileUrl = "http://minio-server/" + bucketName + "/" + fileName;
                evidence.setFileUrl(fileUrl);
            }
        }

        // Update remarks regardless of evidence type.
        if (newRemarks != null) {
            evidence.setRemarks(newRemarks);
        }

        Evidence updatedEvidence = evidenceRepository.save(evidence);
        return new EvidenceDTO(updatedEvidence.getId(), updatedEvidence.getEvidenceType(),
                updatedEvidence.getEvidenceType() == EvidenceType.IMAGE ? updatedEvidence.getFileUrl() : updatedEvidence.getContent(),
                updatedEvidence.getRemarks());
    }

    /**
     * Soft deletes an evidence entry and logs the action.
     * @param evidenceId the ID of the evidence to delete
     * @param performedBy the identifier of the user performing the deletion
     * @param remarks optional remarks for the audit log
     */
    @Transactional
    public void softDeleteEvidence(Long evidenceId, String performedBy, String remarks) {
        Evidence evidence = evidenceRepository.findById(evidenceId)
                .orElseThrow(() -> new RuntimeException("Evidence not found with id: " + evidenceId));

        evidence.setDeleted(true);
        evidenceRepository.save(evidence);

        AuditLog log = new AuditLog("SOFT_DELETE", evidenceId, performedBy, remarks);
        auditLogRepository.save(log);
    }

    /**
     * Hard deletes an evidence entry after proper confirmation.
     *
     * @param evidenceId   ID of the evidence to delete.
     * @param confirmation Should be "yes" to confirm deletion.
     * @param command      Should exactly equal "DELETE <evidenceId>".
     * @param performedBy  Identifier for the user performing the deletion.
     */
    @Transactional
    public void hardDeleteEvidence(Long evidenceId, String confirmation, String command, String performedBy) {
        if (!"yes".equalsIgnoreCase(confirmation)) {
            throw new RuntimeException("Deletion canceled: Confirmation not received as 'yes'.");
        }
        String expectedCommand = "DELETE " + evidenceId;
        if (!expectedCommand.equals(command)) {
            throw new RuntimeException("Deletion canceled: Invalid deletion command.");
        }
        Evidence evidence = evidenceRepository.findById(evidenceId)
                .orElseThrow(() -> new RuntimeException("Evidence not found with id: " + evidenceId));

        // TODO: Validate that the user (performedBy) has proper permissions.

        // Log the hard deletion action for auditing.
        AuditLog log = new AuditLog("HARD_DELETE", evidenceId, performedBy, "Hard deletion confirmed");
        auditLogRepository.save(log);

        // Permanently delete the evidence.
        evidenceRepository.delete(evidence);
    }
}
