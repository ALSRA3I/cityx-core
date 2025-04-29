package com.abdulrahman.cityxcore.service;

import com.abdulrahman.cityxcore.dto.*;
import com.abdulrahman.cityxcore.model.Case;
import com.abdulrahman.cityxcore.model.CrimeReport;
import com.abdulrahman.cityxcore.model.User;
import com.abdulrahman.cityxcore.repository.CaseRepository;
import com.abdulrahman.cityxcore.repository.CrimeReportRepository;
import com.abdulrahman.cityxcore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CaseService {

    @Autowired
    private CrimeReportRepository crimeReportRepository;

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private UserRepository userRepository;

    // Existing methods...

    public CrimeReportDTO submitCrimeReport(String description) {
        CrimeReport report = new CrimeReport(description);
        CrimeReport savedReport = crimeReportRepository.save(report);
        return convertCrimeReportToDTO(savedReport);
    }

    @Transactional
    public CaseDTO createCase(CaseDTO caseDTO) {
        Case aCase = new Case();
        aCase.setCaseNumber(caseDTO.getCaseNumber());
        aCase.setCaseName(caseDTO.getCaseName());
        aCase.setDescription(caseDTO.getDescription());
        aCase.setArea(caseDTO.getArea());
        aCase.setCaseType(caseDTO.getCaseType());
        aCase.setAuthorizationLevel(caseDTO.getAuthorizationLevel());
        Optional<User> userOpt = userRepository.findById(caseDTO.getCreatedById());
        if (userOpt.isPresent()) {
            aCase.setCreatedBy(userOpt.get());
        } else {
            throw new RuntimeException("User not found with id: " + caseDTO.getCreatedById());
        }
        Case savedCase = caseRepository.save(aCase);
        return convertCaseToDTO(savedCase);
    }

    @Transactional
    public CaseDTO updateCase(Long id, CaseDTO caseDTO) {
        Optional<Case> caseOpt = caseRepository.findById(id);
        if (caseOpt.isPresent()) {
            Case aCase = caseOpt.get();
            aCase.setCaseNumber(caseDTO.getCaseNumber());
            aCase.setCaseName(caseDTO.getCaseName());
            aCase.setDescription(caseDTO.getDescription());
            aCase.setArea(caseDTO.getArea());
            aCase.setCaseType(caseDTO.getCaseType());
            aCase.setAuthorizationLevel(caseDTO.getAuthorizationLevel());
            return convertCaseToDTO(aCase);
        } else {
            throw new RuntimeException("Case not found with id: " + id);
        }
    }

    // New method: list cases with optional search
    public List<CaseListingDTO> listCases(String search) {
        List<Case> cases;
        if (search == null || search.trim().isEmpty()) {
            cases = caseRepository.findAll();
        } else {
            cases = caseRepository.findByCaseNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search);
        }
        List<CaseListingDTO> listing = new ArrayList<>();
        for (Case aCase : cases) {
            listing.add(convertToListingDTO(aCase));
        }
        return listing;
    }

    // Helper method to convert a Case to CaseListingDTO with truncated description if necessary
    private CaseListingDTO convertToListingDTO(Case aCase) {
        String description = aCase.getDescription() != null ? aCase.getDescription() : "";
        String truncatedDescription = truncateDescription(description, 100);
        String createdBy = aCase.getCreatedBy() != null ? aCase.getCreatedBy().getUsername() : "Unknown";
        return new CaseListingDTO(
                aCase.getCaseNumber(),
                aCase.getCaseName(),
                truncatedDescription,
                aCase.getArea(),
                aCase.getCaseType(),
                aCase.getAuthorizationLevel(),
                createdBy,
                aCase.getCreatedAt()
        );
    }

    // Truncates the description to maxLength ensuring the last word is complete
    private String truncateDescription(String description, int maxLength) {
        if (description.length() <= maxLength) {
            return description;
        }
        // Get substring up to maxLength
        String sub = description.substring(0, maxLength);
        int lastSpace = sub.lastIndexOf(' ');
        if (lastSpace > 0) {
            sub = sub.substring(0, lastSpace);
        }
        return sub + " ...";
    }

    public CaseDetailsDTO getCaseDetails(Long id) {
        Optional<Case> caseOpt = caseRepository.findById(id);
        if (caseOpt.isPresent()) {
            Case aCase = caseOpt.get();

            // For "Reported By", we use the first associated crime report's reportedBy field if available.
            String reportedBy = aCase.getCrimeReports().isEmpty() ? "Unknown"
                    : aCase.getCrimeReports().get(0).getReportedBy();

            return new CaseDetailsDTO(
                    aCase.getCaseNumber(),
                    aCase.getCaseName(),
                    aCase.getDescription(),
                    aCase.getArea(),
                    aCase.getCreatedAt(),
                    aCase.getCaseType(),
                    aCase.getCaseLevel(),
                    aCase.getAuthorizationLevel(),
                    reportedBy,
                    aCase.getAssignees().size(),
                    aCase.getEvidences().size(),
                    aCase.getSuspects().size(),
                    aCase.getVictims().size(),
                    aCase.getWitnesses().size()
            );
        } else {
            throw new RuntimeException("Case not found with id: " + id);
        }
    }

    public List<UserDTO> getAssignees(Long caseId) {
        Case aCase = findCaseById(caseId);
        return aCase.getAssignees().stream()
                .map(this::convertUserToDTO)
                .collect(Collectors.toList());
    }

    public List<EvidenceDTO> getEvidences(Long caseId) {
        Case aCase = findCaseById(caseId);
        return aCase.getEvidences().stream()
                .map(e -> new EvidenceDTO(e.getId(), e.getContent()))
                .collect(Collectors.toList());
    }

    public List<SuspectDTO> getSuspects(Long caseId) {
        Case aCase = findCaseById(caseId);
        return aCase.getSuspects().stream()
                .map(s -> new SuspectDTO(s.getId(), s.getName()))
                .collect(Collectors.toList());
    }

    public List<VictimDTO> getVictims(Long caseId) {
        Case aCase = findCaseById(caseId);
        return aCase.getVictims().stream()
                .map(v -> new VictimDTO(v.getId(), v.getName()))
                .collect(Collectors.toList());
    }

    public List<WitnessDTO> getWitnesses(Long caseId) {
        Case aCase = findCaseById(caseId);
        return aCase.getWitnesses().stream()
                .map(w -> new WitnessDTO(w.getId(), w.getName()))
                .collect(Collectors.toList());
    }

    private Case findCaseById(Long caseId) {
        return caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found with id: " + caseId));
    }

    // Existing helper methods for converting entities to DTOs

    private CrimeReportDTO convertCrimeReportToDTO(CrimeReport report) {
        return new CrimeReportDTO(report.getId(), report.getReportId(), report.getDescription(),
                report.getStatus(), report.getCreatedAt());
    }

    private CaseDTO convertCaseToDTO(Case aCase) {
        Long createdById = aCase.getCreatedBy() != null ? aCase.getCreatedBy().getId() : null;
        return new CaseDTO(aCase.getId(), aCase.getCaseNumber(), aCase.getCaseName(), aCase.getDescription(),
                aCase.getArea(), aCase.getCaseType(), aCase.getAuthorizationLevel(), createdById, aCase.getCreatedAt());
    }

    private UserDTO convertUserToDTO(User user) {
        // For simplicity, assuming UserDTO has id, username, role, and clearance.
        return new UserDTO(user.getId(), user.getUsername(), null, user.getRole(), user.getClearance());
    }

    public List<String> extractLinksFromCase(Long caseId) {
        Case aCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found with id: " + caseId));
        String description = aCase.getDescription();
        if (description == null || description.trim().isEmpty()) {
            return Collections.emptyList();
        }
        // Simple regex to match URLs starting with http or https
        Pattern urlPattern = Pattern.compile("(https?://\\S+)");
        Matcher matcher = urlPattern.matcher(description);
        List<String> links = new ArrayList<>();
        while (matcher.find()) {
            links.add(matcher.group(1));
        }
        return links;
    }
}
