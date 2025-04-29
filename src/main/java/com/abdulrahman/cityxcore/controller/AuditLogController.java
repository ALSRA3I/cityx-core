package com.abdulrahman.cityxcore.controller;

import com.abdulrahman.cityxcore.dto.AuditLogDTO;
import com.abdulrahman.cityxcore.service.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @Operation(summary = "Get list of audit logs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of audit logs returned",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = AuditLogDTO.class))))
            })
    @GetMapping("/audit-logs")
    public ResponseEntity<List<AuditLogDTO>> getAuditLogs() {
        List<AuditLogDTO> logs = auditLogService.getEvidenceAuditLogs();
        return ResponseEntity.ok(logs);
    }
}