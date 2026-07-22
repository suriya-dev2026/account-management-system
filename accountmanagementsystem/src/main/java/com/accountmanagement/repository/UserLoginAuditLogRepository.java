package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.UserLoginAuditLog;

public interface UserLoginAuditLogRepository extends JpaRepository<UserLoginAuditLog, UUID> {

}
