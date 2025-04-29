package com.abdulrahman.cityxcore.config;

import com.abdulrahman.cityxcore.dto.CaseImportDTO;
import com.abdulrahman.cityxcore.model.Case;
import com.abdulrahman.cityxcore.model.Role;
import com.abdulrahman.cityxcore.model.User;
import com.abdulrahman.cityxcore.repository.CaseRepository;
import com.abdulrahman.cityxcore.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final CaseRepository caseRepository;
    private final UserRepository userRepository;

    public DataLoader(CaseRepository caseRepository, UserRepository userRepository) {
        this.caseRepository = caseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Read the JSON file from the classpath.
        InputStream inputStream = new ClassPathResource("case_data.json").getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        CaseImportDTO importDTO = mapper.readValue(inputStream, CaseImportDTO.class);

        // Create a new Case entity and map basic fields.
        Case aCase = new Case();
        aCase.setCaseNumber(importDTO.getCase_number());
        aCase.setCaseName(importDTO.getCase_name());
        aCase.setDescription(importDTO.getDescription());
        aCase.setArea(importDTO.getArea());
        // Assuming you store city in the area or have a separate field.
        aCase.setCaseType(importDTO.getCase_type());
        aCase.setCaseLevel(importDTO.getLevel());
        // Convert Instant to LocalDateTime.
        LocalDateTime createdAt = LocalDateTime.ofInstant(importDTO.getCreated_at(), ZoneId.systemDefault());
//        aCase.setCreatedAt(createdAt);

        // Map the created_by field.
        CaseImportDTO.CreatorDTO creatorDTO = importDTO.getCreated_by();
        // Try to find an existing user by username.
        User creator = userRepository.findByUsername(creatorDTO.getName()).orElse(null);
        if (creator == null) {
            creator = new User();
            creator.setUsername(creatorDTO.getName());
            // Convert role string to enum. Ensure the role names match your enum (e.g., "ADMIN").
            creator.setRole(Role.valueOf(creatorDTO.getRole().toUpperCase()));
            // You may want to set a default password and clearance for the new user.
            creator.setPassword("defaultPassword");
            creator = userRepository.save(creator);
        }
        aCase.setCreatedBy(creator);

        // Map assignees.
        List<CaseImportDTO.AssigneeDTO> assigneeDTOs = importDTO.getAssignees();
        if (assigneeDTOs != null) {
            for (CaseImportDTO.AssigneeDTO assigneeDTO : assigneeDTOs) {
                // Find or create the user.
                User assignee = userRepository.findByUsername(assigneeDTO.getName()).orElse(null);
                if (assignee == null) {
                    assignee = new User();
                    assignee.setUsername(assigneeDTO.getName());
                    assignee.setRole(Role.valueOf(assigneeDTO.getRole().toUpperCase()));
                    assignee.setPassword("defaultPassword");
                    assignee = userRepository.save(assignee);
                }
                // Add the user to the case's assignees collection.
                aCase.getAssignees().add(assignee);
            }
        }

        // Similarly, map persons and reported_by if your entities support them.
        // For example, you might create Suspect, Victim, and CrimeReport entities here.

        // Save the case entity.
        caseRepository.save(aCase);
        System.out.println("Loaded case: " + aCase.getCaseNumber());
    }
}
