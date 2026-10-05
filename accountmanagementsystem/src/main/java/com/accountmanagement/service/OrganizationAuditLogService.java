package com.accountmanagement.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.accountmanagement.dto.OrganizationAuditLogDto;
import com.accountmanagement.model.OrganizationAuditLog;
import com.accountmanagement.repository.OrganizationAuditLogRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class OrganizationAuditLogService {

    private final OrganizationAuditLogRepository organizationAuditLogRepository;

    public OrganizationAuditLogService(OrganizationAuditLogRepository organizationAuditLogRepository) {
        this.organizationAuditLogRepository = organizationAuditLogRepository;
    }

    public List<OrganizationAuditLogDto> getAllOrganizationLogs() {
        List<OrganizationAuditLog> log = organizationAuditLogRepository.findAll();
        List<OrganizationAuditLogDto> response = new ArrayList<>();
        if (log != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>() {
            }.getType();
            for (OrganizationAuditLog auditLogs : log) {
                OrganizationAuditLogDto auditLogDto = new OrganizationAuditLogDto();
                auditLogDto.setId(auditLogs.getId());
                auditLogDto.setOrganizationCode(auditLogs.getOrganizationCode());
                auditLogDto.setUserId(auditLogs.getUserId());
                auditLogDto.setLoggedtime(auditLogs.getLoggedTime());
                auditLogDto.setEntityName(auditLogs.getEntityName());
                auditLogDto.setEntityPk(auditLogs.getEntityPk());
                if (auditLogs.getUpdatedValue() != null && !auditLogs.getUpdatedValue().isEmpty()) {
                    Map<String, Object> updatedValue = gson.fromJson(auditLogs.getUpdatedValue(), type);
                    auditLogDto.setUpdatedValue(updatedValue);
                }
                if (auditLogs.getExistingValue() != null && !auditLogs.getExistingValue().isEmpty()) {
                    Map<String, Object> existingValue = gson.fromJson(auditLogs.getExistingValue(), type);
                    auditLogDto.setExistingValue(existingValue);
                }
                response.add(auditLogDto);
            }
        }
        return response;
    }

    @Transactional
    public void log(String organizationCode, UUID userId, String entityName,
            String entityPk, String actionName, String existingValue,
            String updatedValue, String remarks, String ipAddress) {
        OrganizationAuditLog organizationAuditLog = new OrganizationAuditLog();
        organizationAuditLog.setOrganizationCode(organizationCode);
        organizationAuditLog.setUserId(userId);
        organizationAuditLog.setEntityName(entityName);
        organizationAuditLog.setEntityPk(entityPk);
        organizationAuditLog.setActionName(actionName);
        organizationAuditLog.setExistingValue(existingValue);
        organizationAuditLog.setUpdatedValue(updatedValue);
        organizationAuditLog.setRemarks(remarks);
        organizationAuditLog.setIpAddress(ipAddress);
        organizationAuditLogRepository.save(organizationAuditLog);

    }

}
