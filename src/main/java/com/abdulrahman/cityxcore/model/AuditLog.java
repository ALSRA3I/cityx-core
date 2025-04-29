package com.abdulrahman.cityxcore.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Action performed (e.g., "SOFT_DELETE")
    private String action;

    // The ID of the evidence that was soft-deleted.
    private Long evidenceId;

    // Who performed the action. (In a real app, this might come from the security context.)
    private String performedBy;

    private LocalDateTime timestamp;

    // Optional remarks.
    private String remarks;

    public AuditLog() {}

    public AuditLog(String action, Long evidenceId, String performedBy, String remarks) {
        this.action = action;
        this.evidenceId = evidenceId;
        this.performedBy = performedBy;
        this.remarks = remarks;
        this.timestamp = LocalDateTime.now();
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
