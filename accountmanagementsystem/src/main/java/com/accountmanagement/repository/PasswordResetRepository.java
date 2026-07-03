package com.accountmanagement.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.PasswordReset;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, String> {

    Optional<PasswordReset> findTopByUserIdOrderByCreatedAtDesc(String id);

    Optional<PasswordReset> findByResetToken(String resetToken);

}
