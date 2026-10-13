package com.accountmanagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.accountmanagement.model.PasswordReset;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {

    Optional<PasswordReset> findTopByUserIdOrderByCreatedAtDesc(UUID id);

    Optional<PasswordReset> findByResetToken(String resetToken);

    @Query(value = "SELECT COUNT(*) FROM password_resets WHERE user_id = :userId AND created_at >= CURRENT_TIMESTAMP - INTERVAL '10 minutes'", nativeQuery = true)
    long countRecentPasswordResetRequests(@Param("userId") UUID userId);

}
