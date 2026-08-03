package com.accountmanagement.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.model.UserVerification;
import com.accountmanagement.repository.UserVerificationRepository;

import jakarta.transaction.Transactional;

@Service
public class UserVerificationService {

    private final UserVerificationRepository userVerificationRepository;

    UserVerificationService(UserVerificationRepository userVerificationRepository) {
        this.userVerificationRepository = userVerificationRepository;
    }

    @Transactional
    public void createUserVerification(UUID userId) {

        UserVerification verification = new UserVerification();
        verification.setUserId(userId);
        verification.setIsUserOnboarded(true);
        verification.setIsEmailVerified(false);
        verification.setIsSubscriptionCompleted(false);
        verification.setProfileCompletedPercentage(25);
        verification.setFailedLoginAttempts(0);
        verification.setIsAccountLocked(false);
        userVerificationRepository.save(verification);
    }
}
