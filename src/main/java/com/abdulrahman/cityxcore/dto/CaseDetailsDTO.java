package com.abdulrahman.cityxcore.dto;

import java.time.LocalDateTime;

public class CaseDetailsDTO {
    private String caseNumber;
    private String caseName;
    private String description;
    private String area;
    private LocalDateTime createdAt;
    private String caseType;
    private String caseLevel;
    private String authorizationLevel;
    private String reportedBy;
    private int numberOfAssignees;
    private int numberOfEvidences;
    private int numberOfSuspects;
    private int numberOfVictims;
    private int numberOfWitnesses;

    public CaseDetailsDTO() {}

    public CaseDetailsDTO(String caseNumber, String caseName, String description, String area, LocalDateTime createdAt,
                          String caseType, String caseLevel, String authorizationLevel, String reportedBy,
                          int numberOfAssignees, int numberOfEvidences, int numberOfSuspects, int numberOfVictims,
                          int numberOfWitnesses) {
        this.caseNumber = caseNumber;
        this.caseName = caseName;
        this.description = description;
        this.area = area;
        this.createdAt = createdAt;
        this.caseType = caseType;
        this.caseLevel = caseLevel;
        this.authorizationLevel = authorizationLevel;
        this.reportedBy = reportedBy;
        this.numberOfAssignees = numberOfAssignees;
        this.numberOfEvidences = numberOfEvidences;
        this.numberOfSuspects = numberOfSuspects;
        this.numberOfVictims = numberOfVictims;
        this.numberOfWitnesses = numberOfWitnesses;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseLevel() {
        return caseLevel;
    }

    public void setCaseLevel(String caseLevel) {
        this.caseLevel = caseLevel;
    }

    public String getAuthorizationLevel() {
        return authorizationLevel;
    }

    public void setAuthorizationLevel(String authorizationLevel) {
        this.authorizationLevel = authorizationLevel;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public int getNumberOfAssignees() {
        return numberOfAssignees;
    }

    public void setNumberOfAssignees(int numberOfAssignees) {
        this.numberOfAssignees = numberOfAssignees;
    }

    public int getNumberOfEvidences() {
        return numberOfEvidences;
    }

    public void setNumberOfEvidences(int numberOfEvidences) {
        this.numberOfEvidences = numberOfEvidences;
    }

    public int getNumberOfSuspects() {
        return numberOfSuspects;
    }

    public void setNumberOfSuspects(int numberOfSuspects) {
        this.numberOfSuspects = numberOfSuspects;
    }

    public int getNumberOfVictims() {
        return numberOfVictims;
    }

    public void setNumberOfVictims(int numberOfVictims) {
        this.numberOfVictims = numberOfVictims;
    }

    public int getNumberOfWitnesses() {
        return numberOfWitnesses;
    }

    public void setNumberOfWitnesses(int numberOfWitnesses) {
        this.numberOfWitnesses = numberOfWitnesses;
    }
}
