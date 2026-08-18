package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.model.OrganizationAuditLog;

import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
public class OrganizationAuditLogListener {

    @PrePersist
    public void onCreate(OrganizationAuditLog organizationAuditLog) {
        organizationAuditLog.setLoggedTime(LocalDateTime.now());
    }
}
