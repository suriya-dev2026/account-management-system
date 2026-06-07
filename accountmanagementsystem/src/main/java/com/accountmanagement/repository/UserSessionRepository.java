package com.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.accountmanagement.model.UserSession;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, String> {

    UserSession findTopByUserIdOrderByCreatedAtDesc(String id);

    UserSession findByRefreshKey(String refreshKey);

}
