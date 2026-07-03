package com.accountmanagement.service;

import com.accountmanagement.repository.UserSessionRepository;
import java.time.LocalDateTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.UserSession;

@Service
public class UserSessionService {

    private final UserSessionRepository userSessionRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    UserSessionService(UserSessionRepository userSessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userSessionRepository = userSessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserSession createUserSession(String userId, String otp) {
        UserSession userSession = new UserSession();
        userSession.setUserId(userId);
        userSession.setOtp(bCryptPasswordEncoder.encode(otp));
        userSession.setOtpExpiration(LocalDateTime.now().plusMinutes(2));
        userSession.setOtpVerificationCount(0);
        userSession.setIsOtpVerified(false);
        userSession.setRefreshKeyStatus(true);
        userSession.setSessionStatus("Active");
        userSession.setIsValidToken(true);
        return userSessionRepository.save(userSession);
    }

    public void updateSessionAfterOtp(String userId, String refreshKey, String accessToken) {
        UserSession session = userSessionRepository.findTopByUserIdOrderByCreatedAtDesc(userId);
        if (session == null) {
            throw new RecordNotFoundException("session not found");
        }
        session.setRefreshKey(refreshKey);
        session.setRefreshKeyCreatedAt(LocalDateTime.now());
        session.setRefreshKeyExpiration(LocalDateTime.now().plusHours(24));
        session.setIsOtpVerified(true);
        session.setOtp(null);
        userSessionRepository.save(session);
    }

    public UserSession getRefreshKey(String refreshKey) {
        UserSession userSession = userSessionRepository.findByRefreshKey(refreshKey);
        if (userSession == null) {
            throw new RecordNotFoundException("Refresh key not found");
        }
        return userSession;
    }

}
