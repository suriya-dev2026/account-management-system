package com.accountmanagement.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.accountmanagement.model.UserSession;

public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {

    Optional<UserSession> findTopByUserIdOrderByCreatedAtDesc(UUID id);

    Optional<UserSession> findByRefreshKey(String refreshKey);

    Optional<UserSession> findByUserId(UUID userId);

    @Query(value = "SELECT COUNT(*) FROM user_sessions WHERE user_id = :userId AND created_at >= CURRENT_TIMESTAMP - INTERVAL '10 minutes'", nativeQuery = true)
    long countRecentOtpRequests(@Param("userId") UUID userId);

}
