package com.accountmanagement.service;

import com.accountmanagement.repository.UserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.accountmanagement.model.UserLog;

@Service
public class UserLogService {

    @Autowired
    private UserLogRepository userLogRepository;

    public UserLog createUserLog(String userId, String action, String resultResult) {
        UserLog userLog = new UserLog();
        userLog.setUserId(userId);
        userLog.setUserAction(action);
        userLog.setReturnResult(resultResult);
        userLogRepository.save(userLog);
        return userLog;
    }
}
