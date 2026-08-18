package com.accountmanagement.service;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.accountmanagement.model.OrganizationAuditLog;
import com.accountmanagement.repository.OrganizationAuditLogRepository;

@Service
public class OrganizationAuditLogService {

    private final OrganizationAuditLogRepository organizationAuditLogRepository;

    public OrganizationAuditLogService(OrganizationAuditLogRepository organizationAuditLogRepository) {
        this.organizationAuditLogRepository = organizationAuditLogRepository;
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

    // @Transactional
    // public Organization updateOrganization(UUID
    // organizationId,OrganizationRequest request, UUID userId, String ipAddress) {

    // Organization organization = organizationService.findById(organizationId);

    // String oldName = organization.getName();

    // organization.setName(request.getName());

    // Organization updatedOrganization = organizationRepository.save(organization);

    // organizationAuditLogService.log(updatedOrganization.getCode(),userId,"Organization",updatedOrganization.getId().toString(),"UPDATE",oldName,
    // updatedOrganization.getName(),"Organization name updated",ipAddress);

    // return updatedOrganization;
    // }

}
