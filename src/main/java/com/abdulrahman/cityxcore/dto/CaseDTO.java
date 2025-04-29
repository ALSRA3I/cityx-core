package com.abdulrahman.cityxcore.dto;

import java.time.LocalDateTime;


public class CaseDTO {
    private Long id;
    private String caseNumber;
    private String caseName;
    private String description;
    private String area;
    private String caseType;
    private String authorizationLevel;
    private Long createdById;
    private LocalDateTime createdAt;

    public CaseDTO() {}

    public CaseDTO(Long id, String caseNumber, String caseName, String description, String area,
                   String caseType, String authorizationLevel, Long createdById, LocalDateTime createdAt) {
        this.id = id;
        this.caseNumber = caseNumber;
        this.caseName = caseName;
        this.description = description;
        this.area = area;
        this.caseType = caseType;
        this.authorizationLevel = authorizationLevel;
        this.createdById = createdById;
        this.createdAt = createdAt;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public String getCaseName() {
        return caseName;
    }

    public String getDescription() {
        return description;
    }

    public String getArea() {
        return area;
    }

    public String getCaseType() {
        return caseType;
    }

    public String getAuthorizationLevel() {
        return authorizationLevel;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public void setAuthorizationLevel(String authorizationLevel) {
        this.authorizationLevel = authorizationLevel;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
