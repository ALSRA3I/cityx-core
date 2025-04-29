package com.abdulrahman.cityxcore.controller;

import com.abdulrahman.cityxcore.dto.EvidenceDTO;
import com.abdulrahman.cityxcore.dto.EvidenceDetailDTO;
import com.abdulrahman.cityxcore.dto.EvidenceImageDTO;
import com.abdulrahman.cityxcore.model.EvidenceType;
import com.abdulrahman.cityxcore.service.EvidenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class EvidenceController {

    @Autowired
    private EvidenceService evidenceService;

    @Operation(summary = "Record evidence for a case",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evidence recorded successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EvidenceDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            })
    @PostMapping("/cases/{caseId}/evidences")
    public ResponseEntity<EvidenceDTO> recordEvidence(
            @PathVariable Long caseId,
            @RequestParam EvidenceType evidenceType,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam(required = false) String remarks
    ) throws Exception {
        EvidenceDTO evidenceDTO = evidenceService.recordEvidence(caseId, evidenceType, content, file, remarks);
        return ResponseEntity.ok(evidenceDTO);
    }

    @Operation(summary = "Retrieve detailed evidence",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evidence details retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EvidenceDetailDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Evidence not found")
            })
    @GetMapping("/evidences/{id}")
    public ResponseEntity<EvidenceDetailDTO> getEvidenceDetail(@PathVariable Long id) throws Exception {
        EvidenceDetailDTO detail = evidenceService.getEvidenceDetail(id);
        return ResponseEntity.ok(detail);
    }

    @Operation(summary = "Retrieve the image of an evidence",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evidence image retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid evidence type")
            })
    @GetMapping("/evidences/{id}/image")
    public ResponseEntity<byte[]> getEvidenceImage(@PathVariable Long id) throws Exception {
        EvidenceImageDTO imageDTO = evidenceService.getEvidenceImage(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, imageDTO.getContentType())
                .body(imageDTO.getData());
    }

    @Operation(summary = "Update existing evidence",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evidence updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EvidenceDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Evidence not found")
            })
    @PutMapping("/evidences/{id}")
    public ResponseEntity<EvidenceDTO> updateEvidence(
            @PathVariable Long id,
            @RequestParam(required = false) String newContent,
            @RequestParam(required = false) MultipartFile newFile,
            @RequestParam(required = false) String newRemarks) throws Exception {
        EvidenceDTO updatedEvidence = evidenceService.updateEvidence(id, newContent, newFile, newRemarks);
        return ResponseEntity.ok(updatedEvidence);
    }

    @Operation(summary = "Soft delete an evidence entry",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Evidence soft deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Evidence not found")
            })
    @DeleteMapping("/evidences/{id}")
    public ResponseEntity<Void> softDeleteEvidence(
            @PathVariable Long id,
            @RequestParam String performedBy,
            @RequestParam(required = false) String remarks) {
        evidenceService.softDeleteEvidence(id, performedBy, remarks);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get the hard delete prompt for an evidence entry",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Hard delete prompt retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
            })
    @GetMapping("/evidences/{id}/hard-delete-prompt")
    public ResponseEntity<String> getHardDeletePrompt(@PathVariable Long id) {
        String prompt = "Are you sure you want to permanently delete Evidence ID: " + id + "? (yes/no)";
        return ResponseEntity.ok(prompt);
    }

    @Operation(summary = "Perform a hard delete of an evidence entry",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evidence permanently deleted"),
                    @ApiResponse(responseCode = "400", description = "Bad request or invalid confirmation")
            })
    @DeleteMapping("/evidences/{id}/hard")
    public ResponseEntity<String> hardDeleteEvidence(
            @PathVariable Long id,
            @RequestParam String confirmation,
            @RequestParam String command,
            @RequestParam String performedBy) {
        evidenceService.hardDeleteEvidence(id, confirmation, command, performedBy);
        return ResponseEntity.ok("Evidence permanently deleted.");
    }
}
