package com.accountmanagement.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.EmailQueue;
import com.accountmanagement.repository.EmailQueueRepository;

@Service
public class EmailQueueService {

    private static final String EMAIL_VERIFICATION_TEMPLATE = """
            Hello, Please verify your email address by clicking the link below: {{verificationLink}}
            This verification link will expire in {{expiry}} minutes. Thank you
            """;

    private static final String PASSWORD_RESET_TEMPLATE = "Your password reset  OTP is: {{otp}}.\n"
            + "This OTP is valid for {{expiry}} MINUTES only";

    private static final String TEMPORARY_PASSWORD_TEMPLATE = """
            Dear User, Your Username: {{username}} Temporary Password: {{password}}
            Please change your temporary password using the link below:
            {{changePasswordLink}}
            Thank you.
            """;

    private static final String LOGIN_OTP_TEMPLATE = """
            Your login OTP is {{otp}}.
            This OTP is valid for {{expiry}} minutes.
            Please do not share this OTP with anyone.
            """;

    private final EmailQueueRepository emailQueueRepository;

    EmailQueueService(EmailQueueRepository emailQueueRepository) {
        this.emailQueueRepository = emailQueueRepository;
    }

    public EmailQueue addToEmailVerificationQueue(UUID userId, String email, String verificationLink) {
        EmailQueue emailQueue = new EmailQueue();
        emailQueue.setUserId(userId);
        emailQueue.setToEmail(email);
        String body = EMAIL_VERIFICATION_TEMPLATE.replace("{{verificationLink}}", verificationLink)
                .replace("{{expiry}}", "2");
        emailQueue.setBody(body);
        emailQueue.setStatus("inprocess");
        emailQueue.setCreatedAt(LocalDateTime.now());
        return emailQueueRepository.save(emailQueue);
    }

    public EmailQueue addToLoginQueue(UUID userId, String email, String otp) {
        EmailQueue emailQueue = new EmailQueue();
        emailQueue.setUserId(userId);
        emailQueue.setToEmail(email);
        String body = LOGIN_OTP_TEMPLATE.replace("{{otp}}", otp).replace("{{expiry}}", "2");
        emailQueue.setBody(body);
        emailQueue.setStatus("inprocess");
        emailQueue.setCreatedAt(LocalDateTime.now());
        return emailQueueRepository.save(emailQueue);
    }

    public EmailQueue addToPasswordResetQueue(UUID userId, String email, String otp) {
        EmailQueue emailQueue = new EmailQueue();
        emailQueue.setUserId(userId);
        emailQueue.setToEmail(email);
        String body = PASSWORD_RESET_TEMPLATE.replace("{{otp}}", otp).replace("{{expiry}}", "2");
        emailQueue.setBody(body);
        emailQueue.setStatus("inprocess");
        emailQueue.setCreatedAt(LocalDateTime.now());
        return emailQueueRepository.save(emailQueue);
    }

    public EmailQueue addToEmailQueue(UUID userId, String email, String body) {
        EmailQueue emailQueue = new EmailQueue();
        emailQueue.setUserId(userId);
        emailQueue.setToEmail(email);
        emailQueue.setBody(body);
        emailQueue.setStatus("inprocess");
        emailQueue.setCreatedAt(LocalDateTime.now());
        return emailQueueRepository.save(emailQueue);
    }

    public EmailQueue addTemporaryPasswordEmail(UUID userId, String email, String username, String temporaryPassword,
            String changePasswordLink) {
        EmailQueue emailQueue = new EmailQueue();
        emailQueue.setUserId(userId);
        emailQueue.setToEmail(email);
        String body = TEMPORARY_PASSWORD_TEMPLATE.replace("{{username}}", username).replace("{{password}}",
                temporaryPassword).replace("{{changePasswordLink}}", changePasswordLink);
        return addToEmailQueue(userId, email, body);
    }

    public EmailQueue findLatestEmailByUserId(UUID userId) {
        return emailQueueRepository
                .findTopByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new RecordNotFoundException("Latest Queue Not Found"));
    }

}
