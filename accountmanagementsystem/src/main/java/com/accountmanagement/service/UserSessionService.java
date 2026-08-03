package com.accountmanagement.service;

import com.accountmanagement.repository.UserSessionRepository;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

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

    public UserSession createUserSession(UUID userId, String otp) {
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

    @Transactional
    public void updateOtp(UUID userId, String otp) {

        UserSession userSession = userSessionRepository.findByUserId(userId)
                .orElseThrow(() -> new RecordNotFoundException("Session not found."));

        userSession.setOtp(bCryptPasswordEncoder.encode(otp));
        userSession.setOtpExpiration(LocalDateTime.now().plusMinutes(2));
        userSession.setOtpVerificationCount(0);
        userSession.setIsOtpVerified(false);
        userSessionRepository.save(userSession);
    }

    public void updateSessionAfterOtp(UUID userId, String refreshKey) {
        UserSession session = userSessionRepository.findTopByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new RecordNotFoundException("User session not found"));
        session.setRefreshKey(refreshKey);
        session.setRefreshKeyCreatedAt(LocalDateTime.now());
        session.setRefreshKeyExpiration(LocalDateTime.now().plusHours(24));
        session.setIsOtpVerified(true);
        session.setOtp(null);
        userSessionRepository.save(session);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void incrementOtpVerificationCount(UUID userId) {
        UserSession session = findByTopUserId(userId);
        session.setOtpVerificationCount(session.getOtpVerificationCount() + 1);
        userSessionRepository.save(session);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markOtpVerified(UUID userId) {
        UserSession session = findByTopUserId(userId);
        session.setIsOtpVerified(true);
        session.setOtpVerificationCount(0);
        userSessionRepository.save(session);
    }

    private UserSession findByTopUserId(UUID userId) {
        return userSessionRepository.findTopByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new RecordNotFoundException("User session not found."));
    }

}
