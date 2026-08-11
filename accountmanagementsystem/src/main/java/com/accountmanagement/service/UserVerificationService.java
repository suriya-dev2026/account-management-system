package com.accountmanagement.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.exceptions.RecordNotFoundException;
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
        verification.setIsUserOnboarded(false);
        verification.setIsEmailVerified(false);
        verification.setIsSubscriptionCompleted(false);
        verification.setProfileCompletedPercentage(AppConstants.REGISTRATION_COMPLETED);
        verification.setFailedLoginAttempts(0);
        verification.setIsAccountLocked(false);
        userVerificationRepository.save(verification);
    }

    @Transactional
    public void completeEmailVerification(UUID userId) {

        UserVerification verification = userVerificationRepository
                .findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User verification not found"));
        verification.setIsEmailVerified(true);
        verification.setProfileCompletedPercentage(AppConstants.EMAIL_VERIFIED_COMPLETED);
        userVerificationRepository.save(verification);
    }

    @Transactional
    public void completeSubscription(UUID userId) {
        UserVerification verification = userVerificationRepository
                .findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User verification not found"));
        verification.setIsSubscriptionCompleted(true);
        verification.setProfileCompletedPercentage(75);
        userVerificationRepository.save(verification);
    }

    public UserVerification findByUserId(UUID id) {
        return userVerificationRepository.findByUserId(id)
                .orElseThrow(() -> new RecordNotFoundException("user id not found"));
    }
}
