package com.accountmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accountmanagement.model.PasswordReset;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, String> {

    Optional<PasswordReset> findTopByUserIdOrderByCreatedAtDesc(String id);

}
