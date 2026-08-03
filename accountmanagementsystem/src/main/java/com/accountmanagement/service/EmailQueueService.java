package com.accountmanagement.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.model.EmailQueue;
import com.accountmanagement.repository.EmailQueueRepository;

@Service
public class EmailQueueService {

    private final EmailQueueRepository emailQueueRepository;

    EmailQueueService(EmailQueueRepository emailQueueRepository) {
        this.emailQueueRepository = emailQueueRepository;
    }

    public EmailQueue addToQueue(UUID userId, String email, String body) {
        EmailQueue emailQueue = new EmailQueue();
        emailQueue.setUserId(userId);
        emailQueue.setToEmail(email);
        emailQueue.setBody(body);
        emailQueue.setStatus("In Process");
        emailQueue.setCreatedAT(LocalDateTime.now());
        return emailQueueRepository.save(emailQueue);
    }
}
