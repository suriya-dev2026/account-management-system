package com.accountmanagement.service;

import com.accountmanagement.repository.EmailQueueRepository;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.accountmanagement.exceptions.InvalidOtpException;
import com.accountmanagement.exceptions.MaxOtpAttemptException;
import com.accountmanagement.exceptions.OtpExpiredException;
import com.accountmanagement.exceptions.OtpNotFoundException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.EmailQueue;
import com.accountmanagement.model.UserSession;
import com.accountmanagement.repository.UserSessionRepository;

import jakarta.transaction.Transactional;

@Service
public class OtpService {

    private final EmailQueueRepository emailQueueRepository;

    private final UserSessionRepository userSessionRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final SecureRandom random = new SecureRandom();

    private final JavaMailSender javaMailSender;

    OtpService(BCryptPasswordEncoder bCryptPasswordEncoder,
            UserSessionRepository userSessionRepository, JavaMailSender javaMailSender,
            EmailQueueRepository emailQueueRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userSessionRepository = userSessionRepository;
        this.javaMailSender = javaMailSender;
        this.emailQueueRepository = emailQueueRepository;
    }

    public String generateOtp() {
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendEmail(EmailQueue emailQueue, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailQueue.getToEmail());
            message.setSubject("Login Otp");
            message.setText("Your otp is" + otp);
            javaMailSender.send(message);
            emailQueue.setStatus("Sent");
            emailQueue.setSentAt(LocalDateTime.now());
        } catch (Exception e) {
            throw new RuntimeException("Failed to send otp email");
        } finally {
            emailQueueRepository.save(emailQueue);
        }
    }

    public void verifyOtp(UUID id, String enteredOtp) {

        UserSession session = userSessionRepository.findTopByUserIdOrderByCreatedAtDesc(id)
                .orElseThrow(() -> new RecordNotFoundException("User session not found"));
        if (session.getOtp() == null) {
            throw new OtpNotFoundException("Otp not found");
        }
        if (session.getOtpVerificationCount() >= 3) {
            throw new MaxOtpAttemptException("Maximum attempts reached");
        }
        if (LocalDateTime.now().isAfter(session.getOtpExpiration())) {
            throw new OtpExpiredException("OTP expired");
        }
        if (!bCryptPasswordEncoder.matches(enteredOtp, session.getOtp())) {
            int attempts = session.getOtpVerificationCount() + 1;
            session.setOtpVerificationCount(attempts);
            userSessionRepository.save(session);
            if (attempts >= 3) {
                throw new MaxOtpAttemptException("Maximum otp verification attempts reached");
            }
            throw new InvalidOtpException("Invalid Otp");
        }
        session.setIsOtpVerified(true);
        session.setOtpVerificationCount(0);
        userSessionRepository.save(session);
    }

}
