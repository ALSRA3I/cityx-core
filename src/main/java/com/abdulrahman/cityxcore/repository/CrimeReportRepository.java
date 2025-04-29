package com.abdulrahman.cityxcore.repository;

import com.abdulrahman.cityxcore.model.CrimeReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrimeReportRepository extends JpaRepository<CrimeReport, Long> {
    Optional<CrimeReport> findByReportId(String reportId);
}
