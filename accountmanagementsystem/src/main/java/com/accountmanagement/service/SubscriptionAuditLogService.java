package com.accountmanagement.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accountmanagement.enums.AuditLogAction;
import com.accountmanagement.model.SubscriptionAuditLog;
import com.accountmanagement.repository.SubscriptionAuditLogRepository;

@Service
public class SubscriptionAuditLogService {

    private final SubscriptionAuditLogRepository subscriptionAuditLogRepository;

    public SubscriptionAuditLogService(SubscriptionAuditLogRepository subscriptionAuditLogRepository) {
        this.subscriptionAuditLogRepository = subscriptionAuditLogRepository;
    }

    @Transactional
    public SubscriptionAuditLog createSubscriptionAuditLog(UUID organizationId, UUID planId, AuditLogAction action,
            String remarks) {
        SubscriptionAuditLog log = new SubscriptionAuditLog();
        log.setOrganizationId(organizationId);
        log.setOldPlanId(null);
        log.setNewPlanId(planId);
        log.setAction(action);
        log.setRemarks(remarks);
        return subscriptionAuditLogRepository.save(log);
    }

}
