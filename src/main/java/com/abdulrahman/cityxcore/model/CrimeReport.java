package com.abdulrahman.cityxcore.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "crime_reports")
public class CrimeReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String reportId;

    @Column(length = 1000)
    private String description;

    private String status;

    private LocalDateTime createdAt;

    // Who reported the crime (could be citizen, admin, or investigator)
    private String reportedBy;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private Case aCase;

    public CrimeReport() {
        this.reportId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.status = "submitted";
    }

    public CrimeReport(String description, String reportedBy) {
        this();
        this.description = description;
        this.reportedBy = reportedBy;
    }

    public CrimeReport(String description) {
        this();
        this.description = description;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public Case getACase() {
        return aCase;
    }

    public void setACase(Case aCase) {
        this.aCase = aCase;
    }
}
