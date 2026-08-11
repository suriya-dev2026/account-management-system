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
        userSession.setRefreshKeyStatus(false);
        userSession.setSessionStatus("Active");
        userSession.setIsValidToken(false);
        UserSession saved = userSessionRepository.save(userSession);
        return saved;

    }

    // @Transactional
    // public void updateOtp(UUID userId, String otp) {

    // UserSession userSession = userSessionRepository.findByUserId(userId)
    // .orElseThrow(() -> new RecordNotFoundException("Session not found."));

    // userSession.setOtp(bCryptPasswordEncoder.encode(otp));
    // userSession.setOtpExpiration(LocalDateTime.now().plusMinutes(2));
    // userSession.setOtpVerificationCount(0);
    // userSession.setIsOtpVerified(false);

    // UserSession updatedOtp = userSessionRepository.save(userSession);
    // System.out.println("UPDATED OTP" + updatedOtp.getIsOtpVerified());
    // }

    public void updateSessionAfterOtp(UUID userId, String refreshKey) {
        UserSession session = userSessionRepository.findTopByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new RecordNotFoundException("User session not found"));
        session.setRefreshKey(refreshKey);
        session.setRefreshKeyStatus(true);
        session.setRefreshKeyCreatedAt(LocalDateTime.now());
        session.setRefreshKeyExpiration(LocalDateTime.now().plusHours(24));
        session.setIsOtpVerified(true);
        session.setOtp(null);
        userSessionRepository.save(session);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int incrementOtpVerificationCount(UUID userId) {
        UserSession session = findByTopUserId(userId);
        int updatedCount = session.getOtpVerificationCount() + 1;
        session.setOtpVerificationCount(updatedCount);
        session.setIsOtpVerified(false);
        UserSession saved = userSessionRepository.save(session);
        System.out.println("INCREMENT OTP" + saved.getIsOtpVerified());
        return updatedCount;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markOtpVerified(UUID userId) {
        UserSession session = findByTopUserId(userId);
        session.setIsOtpVerified(true);
        session.setOtpVerificationCount(0);
        UserSession saved = userSessionRepository.save(session);
        System.out.println("MARK OTP" + saved.getIsOtpVerified());
    }

    private UserSession findByTopUserId(UUID userId) {
        return userSessionRepository.findTopByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new RecordNotFoundException("User session not found."));
    }

}
