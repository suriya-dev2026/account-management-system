package com.accountmanagement.service;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.UserVerificationMessage;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.UserVerification;
import com.accountmanagement.repository.UserVerificationRepository;

@Service
public class UserVerificationService {

    private final UserVerificationRepository userVerificationRepository;

    UserVerificationService(UserVerificationRepository userVerificationRepository) {
        this.userVerificationRepository = userVerificationRepository;
    }

    @Transactional
    public void completUserRegistration(UUID userId) {
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
                .orElseThrow(() -> new RuntimeException(UserVerificationMessage.VERIFICATION_RECORD_NOT_FOUND));
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateFailedLoginAttempt(UserVerification userVerification) {
        int attempts = userVerification.getFailedLoginAttempts() + 1;
        userVerification.setFailedLoginAttempts(attempts);
        if (attempts >= 3) {
            userVerification.setIsAccountLocked(true);
            userVerification.setLockedTime(
                    LocalDateTime.now());
        }
        userVerificationRepository.save(userVerification);
    }
}
