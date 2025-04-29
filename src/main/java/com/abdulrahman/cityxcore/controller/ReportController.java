package com.abdulrahman.cityxcore.controller;

import com.abdulrahman.cityxcore.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Operation(summary = "Generate a report for a specific case",
            description = "Generates a PDF report for the case with the specified case ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "PDF report generated successfully",
                            content = @Content(mediaType = "application/pdf", schema = @Schema(type = "string", format = "byte"))),
                    @ApiResponse(responseCode = "500", description = "Internal server error while generating the report",
                            content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
            })
    @GetMapping("/cases/{caseId}/report")
    public ResponseEntity<byte[]> generateCaseReport(@PathVariable Long caseId) {
        try {
            byte[] pdfBytes = reportService.generateCaseReportPDF(caseId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"case-report-" + caseId + ".pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Get the status of a case report",
            description = "Returns the status of the case based on the given report ID. This is for public access.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status of the case report retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                    @ApiResponse(responseCode = "404", description = "Report not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
            })
    @GetMapping("/public/reports/{reportId}/status")
    public ResponseEntity<String> getCaseStatus(@PathVariable String reportId) {
        try {
            String status = reportService.getCaseStatusByReportId(reportId);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Report not found");
        }
    }
}
