package com.abdulrahman.cityxcore.dto;

import com.abdulrahman.cityxcore.model.EvidenceType;


public class EvidenceDetailDTO {
    private Long id;
    private EvidenceType evidenceType;
    // For text evidence, this field holds the text; for image evidence, it holds the file URL.
    private String content;
    private String remarks;
    // For image evidence, this field holds the size of the image in bytes.
    private Long imageSize;

    public EvidenceDetailDTO() {}

    public EvidenceDetailDTO(Long id, EvidenceType evidenceType, String content, String remarks, Long imageSize) {
        this.id = id;
        this.evidenceType = evidenceType;
        this.content = content;
        this.remarks = remarks;
        this.imageSize = imageSize;
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

    public Long getImageSize() {
        return imageSize;
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

    public void setImageSize(Long imageSize) {
        this.imageSize = imageSize;
    }
}
