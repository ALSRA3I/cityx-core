package com.abdulrahman.cityxcore.service;

import com.abdulrahman.cityxcore.dto.AuditLogDTO;
import com.abdulrahman.cityxcore.model.AuditLog;
import com.abdulrahman.cityxcore.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public List<AuditLogDTO> getEvidenceAuditLogs() {
        List<AuditLog> logs = auditLogRepository.findAll();
        return logs.stream()
                .map(log -> new AuditLogDTO(log.getId(), log.getAction(), log.getEvidenceId(),
                        log.getPerformedBy(), log.getTimestamp(), log.getRemarks()))
                .collect(Collectors.toList());
    }
}
