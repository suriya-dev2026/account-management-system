package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.SubscriptionAuditLog;

public interface SubscriptionAuditLogRepository extends JpaRepository<SubscriptionAuditLog, UUID> {

}
