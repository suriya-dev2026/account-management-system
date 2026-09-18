package com.accountmanagement.service;

import com.accountmanagement.repository.EmailQueueRepository;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.UserMessage;
import com.accountmanagement.enums.DeliveryStatus;
import com.accountmanagement.exceptions.InvalidOtpException;
import com.accountmanagement.exceptions.MaxOtpAttemptException;
import com.accountmanagement.exceptions.OtpExpiredException;
import com.accountmanagement.exceptions.OtpNotFoundException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.EmailQueue;
import com.accountmanagement.model.UserSession;
import com.accountmanagement.repository.UserSessionRepository;

@Service
public class OtpService {

    private final EmailQueueRepository emailQueueRepository;

    private final UserSessionRepository userSessionRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final SecureRandom random = new SecureRandom();

    private final JavaMailSender javaMailSender;

    private final BroadcastDeliveryService broadcastDeliveryService;

    OtpService(BCryptPasswordEncoder bCryptPasswordEncoder,
            UserSessionRepository userSessionRepository, JavaMailSender javaMailSender,
            EmailQueueRepository emailQueueRepository, BroadcastDeliveryService broadcastDeliveryService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userSessionRepository = userSessionRepository;
        this.javaMailSender = javaMailSender;
        this.emailQueueRepository = emailQueueRepository;
        this.broadcastDeliveryService = broadcastDeliveryService;
    }

    public String generateOtp() {
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendEmail(EmailQueue emailQueue) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailQueue.getToEmail());
            message.setText(emailQueue.getBody());
            javaMailSender.send(message);
            emailQueue.setStatus(AppConstants.SENT);
            emailQueue.setSentAt(LocalDateTime.now());
            broadcastDeliveryService.updateBroadcastDeliveryStatus(
                    emailQueue.getUserId(),
                    emailQueue.getToEmail(),
                    DeliveryStatus.SENT);
        } catch (Exception e) {
            e.printStackTrace();
            emailQueue.setStatus(AppConstants.FAILED);
            broadcastDeliveryService.updateBroadcastDeliveryStatus(
                    emailQueue.getUserId(),
                    emailQueue.getToEmail(),
                    DeliveryStatus.FAILED);
        } finally {
            emailQueueRepository.save(emailQueue);
        }
    }

    public void verifyOtp(UUID id, String enteredOtp) {

        UserSession session = userSessionRepository.findTopByUserIdOrderByCreatedAtDesc(id)
                .orElseThrow(() -> new RecordNotFoundException(UserMessage.USER_SESSION_NOT_FOUND));
        if (session.getOtp() == null) {
            throw new OtpNotFoundException(UserMessage.OTP_NOT_FOUND);
        }
        if (session.getOtpVerificationCount() >= 3) {
            throw new MaxOtpAttemptException(UserMessage.MAXIMUM_ATTEMPTS_REACHED);
        }
        if (LocalDateTime.now().isAfter(session.getOtpExpiration())) {
            throw new OtpExpiredException(UserMessage.OTP_EXPIRED);
        }
        if (!bCryptPasswordEncoder.matches(enteredOtp, session.getOtp())) {
            int attempts = session.getOtpVerificationCount() + 1;
            session.setOtpVerificationCount(attempts);
            userSessionRepository.save(session);
            if (attempts >= 3) {
                throw new MaxOtpAttemptException(UserMessage.MAXIMUM_ATTEMPTS_REACHED);
            }
            throw new InvalidOtpException(UserMessage.INVALID_OTP);
        }
        session.setIsOtpVerified(true);
        session.setOtpVerificationCount(0);
        userSessionRepository.save(session);
    }

}
