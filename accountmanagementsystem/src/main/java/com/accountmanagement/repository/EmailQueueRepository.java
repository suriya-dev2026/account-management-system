package com.accountmanagement.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.accountmanagement.model.EmailQueue;
import jakarta.transaction.Transactional;

public interface EmailQueueRepository extends JpaRepository<EmailQueue, UUID> {

    List<EmailQueue> findByStatus(String string);

    Optional<EmailQueue> findTopByUserIdOrderByCreatedAtDesc(UUID userId);

    @Modifying
    @Transactional
    @Query(value = """
            Delete from EmailQueue where id IN (
            select id from email_queue where status = "sent" and sent_at < :cutoff
            LIMIT 10)
            """, nativeQuery = true)
    int deleteOldSentEmails(@Param("cutoff") LocalDateTime cutoff);

}
