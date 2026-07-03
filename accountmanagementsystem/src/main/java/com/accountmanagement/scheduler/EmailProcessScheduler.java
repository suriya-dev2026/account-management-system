package com.accountmanagement.scheduler;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.accountmanagement.model.EmailQueue;
import com.accountmanagement.repository.EmailQueueRepository;

@Component
public class EmailProcessScheduler {

    private final EmailQueueRepository emailQueueRepository;

    private final JavaMailSender javaMailSender;

    EmailProcessScheduler(EmailQueueRepository emailQueueRepository, JavaMailSender javaMailSender) {
        this.emailQueueRepository = emailQueueRepository;
        this.javaMailSender = javaMailSender;
    }

    @Scheduled(fixedDelay = 30000)
    public void processEmails() {
        List<EmailQueue> emails = emailQueueRepository.findByStatus("In Process");
        for (EmailQueue email : emails) {
            try {
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(email.getToEmail());
                simpleMailMessage.setSubject("login otp");
                simpleMailMessage.setText(email.getMessage());
                javaMailSender.send(simpleMailMessage);
                email.setStatus("Sent");
                email.setSentAt(LocalDateTime.now());
            } catch (Exception ex) {
                email.setStatus("Failed");
            }
            emailQueueRepository.save(email);
        }
    }
}
