package com.accountmanagement.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.EmailQueue;
import com.accountmanagement.repository.EmailQueueRepository;

@Service
public class EmailQueueService {

    private final EmailQueueRepository emailQueueRepository;

    private static final String EMAIL_VERIFICATION_TEMPLATE = "Your email verification OTP is: {{otp}}.\n"
            + "This OTP is valid for {{expiry}} MINUTES only";

    private static final String PASSWORD_RESET_TEMPLATE = "Your password reset  OTP is: {{otp}}.\n"
            + "This OTP is valid for {{expiry}} MINUTES only";

    private static final String TEMPORARY_PASSWORD_TEMPLATE = """
            Dear User, Your subscription has been activated successfully. Username: {{username}} Temporary Password: {{password}}
            Please login and change your password immediately.
            Thank you.
            """;

    private static final String LOGIN_OTP_TEMPLATE = """
            Your login OTP is {{otp}}.
            This OTP is valid for {{expiry}} minutes.
            Please do not share this OTP with anyone.
            """;

    EmailQueueService(EmailQueueRepository emailQueueRepository) {
        this.emailQueueRepository = emailQueueRepository;
    }

    public EmailQueue addToEmailVerificationQueue(UUID userId, String email, String otp) {
        EmailQueue emailQueue = new EmailQueue();
        emailQueue.setUserId(userId);
        emailQueue.setToEmail(email);
        String body = EMAIL_VERIFICATION_TEMPLATE.replace("{{otp}}", otp).replace("{{expiry}}", "2");
        emailQueue.setBody(body);
        emailQueue.setStatus("In Process");
        emailQueue.setCreatedAt(LocalDateTime.now());
        return emailQueueRepository.save(emailQueue);
    }

    public EmailQueue addToLoginQueue(UUID userId, String email, String otp) {
        EmailQueue emailQueue = new EmailQueue();
        emailQueue.setUserId(userId);
        emailQueue.setToEmail(email);
        String body = LOGIN_OTP_TEMPLATE.replace("{{otp}}", otp).replace("{{expiry}}", "2");
        emailQueue.setBody(body);
        emailQueue.setStatus("In Process");
        emailQueue.setCreatedAt(LocalDateTime.now());
        return emailQueueRepository.save(emailQueue);
    }

    public EmailQueue addToPasswordResetQueue(UUID userId, String email, String otp) {
        EmailQueue emailQueue = new EmailQueue();
        emailQueue.setUserId(userId);
        emailQueue.setToEmail(email);
        String body = PASSWORD_RESET_TEMPLATE.replace("{{otp}}", otp).replace("{{expiry}}", "2");
        emailQueue.setBody(body);
        emailQueue.setStatus("In Process");
        emailQueue.setCreatedAt(LocalDateTime.now());
        return emailQueueRepository.save(emailQueue);
    }

    public EmailQueue addToEmailQueue(UUID userId, String email, String body) {
        EmailQueue emailQueue = new EmailQueue();
        emailQueue.setUserId(userId);
        emailQueue.setToEmail(email);
        emailQueue.setBody(body);
        emailQueue.setStatus("In Process");
        emailQueue.setCreatedAt(LocalDateTime.now());
        return emailQueueRepository.save(emailQueue);
    }

    public EmailQueue addTemporaryPasswordEmail(UUID userId, String email, String username, String temporaryPassword) {
        EmailQueue emailQueue = new EmailQueue();
        emailQueue.setUserId(userId);
        emailQueue.setToEmail(email);
        String body = TEMPORARY_PASSWORD_TEMPLATE.replace("{{username}}", username).replace("{{password}}",
                temporaryPassword);
        return addToEmailQueue(userId, email, body);
    }

    public EmailQueue findLatestEmailByUserId(UUID userId) {
        return emailQueueRepository
                .findTopByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new RecordNotFoundException("Latest Queue Not Found"));
    }

}
