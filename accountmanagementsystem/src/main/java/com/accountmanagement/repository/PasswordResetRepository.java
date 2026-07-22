package com.accountmanagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.PasswordReset;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {

    Optional<PasswordReset> findTopByUserIdOrderByCreatedAtDesc(UUID id);

    Optional<PasswordReset> findByResetToken(String resetToken);

}
