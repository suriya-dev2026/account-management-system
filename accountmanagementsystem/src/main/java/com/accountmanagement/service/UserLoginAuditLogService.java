package com.accountmanagement.service;

import com.accountmanagement.model.UserLoginAuditLog;
import com.accountmanagement.repository.UserLoginAuditLogRepository;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserLoginAuditLogService {

    private final UserLoginAuditLogRepository userLogRepository;

    UserLoginAuditLogService(UserLoginAuditLogRepository userLogRepository) {
        this.userLogRepository = userLogRepository;
    }

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
