package com.abdulrahman.cityxcore.controller;

import com.abdulrahman.cityxcore.dto.*;
import com.abdulrahman.cityxcore.service.CaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CaseController {

    @Autowired
    private CaseService caseService;

    @Operation(summary = "Submit a crime report",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Crime report submitted successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CrimeReportDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            })
    @PostMapping("/crime-reports")
    public ResponseEntity<CrimeReportDTO> submitCrimeReport(@RequestBody String description) {
        CrimeReportDTO reportDTO = caseService.submitCrimeReport(description);
        return ResponseEntity.ok(reportDTO);
    }

    @Operation(summary = "Create a new case",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Case created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CaseDTO.class)))
            })
    @PostMapping("/cases")
    public ResponseEntity<CaseDTO> createCase(@RequestBody CaseDTO caseDTO) {
        CaseDTO createdCase = caseService.createCase(caseDTO);
        return ResponseEntity.ok(createdCase);
    }

    @Operation(summary = "Update an existing case",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Case updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CaseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Case not found")
            })
    @PutMapping("/cases/{id}")
    public ResponseEntity<CaseDTO> updateCase(@PathVariable Long id, @RequestBody CaseDTO caseDTO) {
        CaseDTO updatedCase = caseService.updateCase(id, caseDTO);
        return ResponseEntity.ok(updatedCase);
    }

    @Operation(summary = "List all cases",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of cases",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CaseListingDTO.class)))
            })
    @GetMapping("/cases")
    public ResponseEntity<List<CaseListingDTO>> listCases(@RequestParam(required = false) String search) {
        List<CaseListingDTO> cases = caseService.listCases(search);
        return ResponseEntity.ok(cases);
    }

    @Operation(summary = "Get case details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Case details",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CaseDetailsDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Case not found")
            })
    @GetMapping("/cases/{id}/details")
    public ResponseEntity<CaseDetailsDTO> getCaseDetails(@PathVariable Long id) {
        CaseDetailsDTO detailsDTO = caseService.getCaseDetails(id);
        return ResponseEntity.ok(detailsDTO);
    }

    @Operation(summary = "Get assignees of a case",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of assignees",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
            })
    @GetMapping("/cases/{id}/assignees")
    public ResponseEntity<List<UserDTO>> getAssignees(@PathVariable Long id) {
        List<UserDTO> assignees = caseService.getAssignees(id);
        return ResponseEntity.ok(assignees);
    }

    @Operation(summary = "Get evidences of a case",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of evidences",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EvidenceDTO.class)))
            })
    @GetMapping("/cases/{id}/evidences")
    public ResponseEntity<List<EvidenceDTO>> getEvidences(@PathVariable Long id) {
        List<EvidenceDTO> evidences = caseService.getEvidences(id);
        return ResponseEntity.ok(evidences);
    }

    @Operation(summary = "Get suspects of a case",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of suspects",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuspectDTO.class)))
            })
    @GetMapping("/cases/{id}/suspects")
    public ResponseEntity<List<SuspectDTO>> getSuspects(@PathVariable Long id) {
        List<SuspectDTO> suspects = caseService.getSuspects(id);
        return ResponseEntity.ok(suspects);
    }

    @Operation(summary = "Get victims of a case",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of victims",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VictimDTO.class)))
            })
    @GetMapping("/cases/{id}/victims")
    public ResponseEntity<List<VictimDTO>> getVictims(@PathVariable Long id) {
        List<VictimDTO> victims = caseService.getVictims(id);
        return ResponseEntity.ok(victims);
    }

    @Operation(summary = "Get witnesses of a case",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of witnesses",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = WitnessDTO.class)))
            })
    @GetMapping("/cases/{id}/witnesses")
    public ResponseEntity<List<WitnessDTO>> getWitnesses(@PathVariable Long id) {
        List<WitnessDTO> witnesses = caseService.getWitnesses(id);
        return ResponseEntity.ok(witnesses);
    }

    @Operation(summary = "Extract links from case description",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of extracted URLs",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(type = "string"))))
            })
    @GetMapping("/cases/{id}/links")
    public ResponseEntity<List<String>> extractLinks(@PathVariable Long id) {
        List<String> links = caseService.extractLinksFromCase(id);
        return ResponseEntity.ok(links);
    }
}