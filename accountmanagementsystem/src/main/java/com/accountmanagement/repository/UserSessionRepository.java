package com.accountmanagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.UserSession;

public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {

    Optional<UserSession> findTopByUserIdOrderByCreatedAtDesc(UUID id);

    Optional<UserSession> findByRefreshKey(String refreshKey);

    Optional<UserSession> findByUserId(UUID userId);

}
