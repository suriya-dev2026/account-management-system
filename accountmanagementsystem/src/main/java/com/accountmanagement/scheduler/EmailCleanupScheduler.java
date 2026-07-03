package com.accountmanagement.scheduler;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import com.accountmanagement.repository.EmailQueueRepository;

@Component
public class EmailCleanupScheduler {

    private final EmailQueueRepository emailQueueRepository;

    EmailCleanupScheduler(EmailQueueRepository emailQueueRepository) {
        this.emailQueueRepository = emailQueueRepository;
    }

    public void deleteSentEmails() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(7);
        emailQueueRepository.deleteOldSentEmails(cutoff);
    }

}
