package com.abdulrahman.cityxcore.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cases")
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String caseNumber;

    private String caseName;

    @Column(length = 1000)
    private String description;

    private String area;

    private String caseType;

    private String caseLevel;

    private String authorizationLevel;

    private LocalDateTime createdAt;

    // Linked to the user who created the case.
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    // A case can be linked to multiple crime reports.
    @OneToMany(mappedBy = "aCase", cascade = CascadeType.ALL)
    private List<CrimeReport> crimeReports = new ArrayList<>();

    // Many-to-Many relationship for assigned officers (assignees)
    @ManyToMany
    @JoinTable(name = "case_assignees",
            joinColumns = @JoinColumn(name = "case_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> assignees = new ArrayList<>();

    // One-to-Many relationships for evidences, suspects, victims, and witnesses.
    @OneToMany(mappedBy = "aCase", cascade = CascadeType.ALL)
    private List<Evidence> evidences = new ArrayList<>();

    @OneToMany(mappedBy = "aCase", cascade = CascadeType.ALL)
    private List<Suspect> suspects = new ArrayList<>();

    @OneToMany(mappedBy = "aCase", cascade = CascadeType.ALL)
    private List<Victim> victims = new ArrayList<>();

    @OneToMany(mappedBy = "aCase", cascade = CascadeType.ALL)
    private List<Witness> witnesses = new ArrayList<>();

    public Case() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public List<CrimeReport> getCrimeReports() {
        return crimeReports;
    }

    public void setCrimeReports(List<CrimeReport> crimeReports) {
        this.crimeReports = crimeReports;
    }

    public List<User> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<User> assignees) {
        this.assignees = assignees;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }

    public void setEvidences(List<Evidence> evidences) {
        this.evidences = evidences;
    }

    public List<Suspect> getSuspects() {
        return suspects;
    }

    public void setSuspects(List<Suspect> suspects) {
        this.suspects = suspects;
    }

    public List<Victim> getVictims() {
        return victims;
    }

    public void setVictims(List<Victim> victims) {
        this.victims = victims;
    }

    public List<Witness> getWitnesses() {
        return witnesses;
    }

    public void setWitnesses(List<Witness> witnesses) {
        this.witnesses = witnesses;
    }
}

