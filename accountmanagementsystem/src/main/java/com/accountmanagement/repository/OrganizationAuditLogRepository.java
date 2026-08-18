package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.OrganizationAuditLog;

public interface OrganizationAuditLogRepository extends JpaRepository<OrganizationAuditLog, UUID> {

}
