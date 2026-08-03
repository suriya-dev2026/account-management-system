package com.accountmanagement.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.accountmanagement.model.EmailQueue;
import com.accountmanagement.repository.EmailQueueRepository;
import com.accountmanagement.service.OtpService;

import jakarta.transaction.Transactional;

@Component
public class EmailScheduler {

    private final EmailQueueRepository emailQueueRepository;

    private final OtpService otpService;

    public EmailScheduler(EmailQueueRepository emailQueueRepository, OtpService otpService) {
        this.emailQueueRepository = emailQueueRepository;
        this.otpService = otpService;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void processEmailQueue() {
        List<EmailQueue> emails = emailQueueRepository.findByStatus("In Process");

        for (EmailQueue email : emails) {
            otpService.sendEmail(email);
        }
    }
}
