package com.abdulrahman.cityxcore.dto;

import java.time.LocalDateTime;

public class CaseListingDTO {
    private String caseNumber;
    private String caseName;
    private String description;
    private String area;
    private String caseType;
    private String authorizationLevel;
    private String createdBy; // using username for clarity
    private LocalDateTime createdAt;

    public CaseListingDTO() {}

    public CaseListingDTO(String caseNumber, String caseName, String description, String area,
                          String caseType, String authorizationLevel, String createdBy, LocalDateTime createdAt) {
        this.caseNumber = caseNumber;
        this.caseName = caseName;
        this.description = description;
        this.area = area;
        this.caseType = caseType;
        this.authorizationLevel = authorizationLevel;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    // Getters and setters

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getAuthorizationLevel() {
        return authorizationLevel;
    }

    public void setAuthorizationLevel(String authorizationLevel) {
        this.authorizationLevel = authorizationLevel;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
