package com.accountmanagement.service;

import com.accountmanagement.model.UserLoginAuditLog;
import com.accountmanagement.repository.UserLoginAuditLogRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginAuditLogService {

    private final UserLoginAuditLogRepository userLogRepository;

    public UserLoginAuditLog createUserLog(UUID organizationId, UUID userId, String action, String resultResult) {
        UserLoginAuditLog userLog = new UserLoginAuditLog();
        userLog.setOrganizationId(organizationId);
        userLog.setUserId(userId);
        userLog.setLoggedTime(LocalDateTime.now());
        userLog.setAction(action);
        userLog.setReturnedResult(resultResult);
        userLogRepository.save(userLog);
        return userLog;
    }
}
