package com.abdulrahman.cityxcore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "evidences")
public class Evidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // For text evidence, store the text here.
    @Column(length = 2000)
    private String content;

    // Optional remarks for any evidence.
    private String remarks;

    // Distinguish between text and image evidence.
    @Enumerated(EnumType.STRING)
    private EvidenceType evidenceType;

    // For image evidence, store the URL of the uploaded file.
    private String fileUrl;

    // Soft delete flag. When true, the record is considered deleted.
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private Case aCase;

    public Evidence() {}

    // Getters and setters

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public EvidenceType getEvidenceType() {
        return evidenceType;
    }

    public void setEvidenceType(EvidenceType evidenceType) {
        this.evidenceType = evidenceType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Case getACase() {
        return aCase;
    }

    public void setACase(Case aCase) {
        this.aCase = aCase;
    }
}
