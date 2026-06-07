package com.accountmanagement.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.accountmanagement.model.EmailQueue;
import jakarta.transaction.Transactional;

@Repository
public interface EmailQueueRepository extends JpaRepository<EmailQueue, String> {

    List<EmailQueue> findByStatus(String string);

    @Modifying
    @Transactional
    @Query(value = """
            Delete from EmailQueue where id IN (
            select id from email_queue where status = "sent" and sent_at < :cutoff
            LIMIT 10)
            """, nativeQuery = true)
    int deleteOldSentEmails(@Param("cutoff") LocalDateTime cutoff);

}
