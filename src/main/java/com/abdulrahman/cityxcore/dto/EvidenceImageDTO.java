package com.abdulrahman.cityxcore.dto;

public class EvidenceImageDTO {
    private byte[] data;
    private String contentType;

    public EvidenceImageDTO() {}

    public EvidenceImageDTO(byte[] data, String contentType) {
        this.data = data;
        this.contentType = contentType;
    }

    // Getters and setters

    public byte[] getData() {
        return data;
    }

    public String getContentType() {
        return contentType;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
