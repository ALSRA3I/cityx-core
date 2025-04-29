package com.abdulrahman.cityxcore.repository;

import com.abdulrahman.cityxcore.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CaseRepository extends JpaRepository<Case, Long> {
    List<Case> findByCaseNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String caseName, String description);
}
