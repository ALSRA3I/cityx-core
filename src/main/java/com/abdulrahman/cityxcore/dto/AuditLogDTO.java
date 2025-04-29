package com.abdulrahman.cityxcore.dto;

import java.time.LocalDateTime;

public class AuditLogDTO {
    private Long id;
    private String action;
    private Long evidenceId;
    private String performedBy;
    private LocalDateTime timestamp;
    private String remarks;

    public AuditLogDTO() {}

    public AuditLogDTO(Long id, String action, Long evidenceId, String performedBy, LocalDateTime timestamp, String remarks) {
        this.id = id;
        this.action = action;
        this.evidenceId = evidenceId;
        this.performedBy = performedBy;
        this.timestamp = timestamp;
        this.remarks = remarks;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public Long getEvidenceId() {
        return evidenceId;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setEvidenceId(Long evidenceId) {
        this.evidenceId = evidenceId;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
