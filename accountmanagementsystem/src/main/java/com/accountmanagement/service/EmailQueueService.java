package com.accountmanagement.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accountmanagement.model.EmailQueue;
import com.accountmanagement.repository.EmailQueueRepository;

@Service
public class EmailQueueService {

    @Autowired
    private EmailQueueRepository emailQueueRepository;

    public void addToQueue(String userId, String email, String otp) {
        EmailQueue emailQueue = new EmailQueue();
        emailQueue.setUserId(userId);
        emailQueue.setToEmail(email);
        emailQueue.setMessage("Your otp  is" + otp);
        emailQueue.setStatus("In Process");
        emailQueue.setCreatedAT(LocalDateTime.now());
        emailQueueRepository.save(emailQueue);
    }
}
