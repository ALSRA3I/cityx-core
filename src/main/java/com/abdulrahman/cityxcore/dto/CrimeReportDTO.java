package com.abdulrahman.cityxcore.dto;

import java.time.LocalDateTime;


public class CrimeReportDTO {
    private Long id;
    private String reportId;
    private String description;
    private String status;
    private LocalDateTime createdAt;

    public CrimeReportDTO() {}

    public CrimeReportDTO(Long id, String reportId, String description, String status, LocalDateTime createdAt) {
        this.id = id;
        this.reportId = reportId;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public String getReportId() {
        return reportId;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
