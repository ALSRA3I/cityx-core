package com.abdulrahman.cityxcore.dto;

import com.abdulrahman.cityxcore.model.EvidenceType;


public class EvidenceDTO {
    private Long id;
    private EvidenceType evidenceType;
    // For text evidence, this field holds the text; for image evidence, it holds the file URL.
    private String content;
    private String remarks;

    public EvidenceDTO() {}

    public EvidenceDTO(Long id, EvidenceType evidenceType, String content, String remarks) {
        this.id = id;
        this.evidenceType = evidenceType;
        this.content = content;
        this.remarks = remarks;
    }

    public EvidenceDTO(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public EvidenceType getEvidenceType() {
        return evidenceType;
    }

    public String getContent() {
        return content;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEvidenceType(EvidenceType evidenceType) {
        this.evidenceType = evidenceType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
