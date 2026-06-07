package com.accountmanagement.scheduler;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.accountmanagement.repository.EmailQueueRepository;

@Component
public class EmailCleanupScheduler {

    @Autowired
    private EmailQueueRepository emailQueueRepository;

    public void deleteSentEmails() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(7);
        emailQueueRepository.deleteOldSentEmails(cutoff);
    }

}
