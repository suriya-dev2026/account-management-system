package com.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.UserSession;

public interface UserSessionRepository extends JpaRepository<UserSession, String> {

    UserSession findTopByUserIdOrderByCreatedAtDesc(String id);

    UserSession findByRefreshKey(String refreshKey);

}
