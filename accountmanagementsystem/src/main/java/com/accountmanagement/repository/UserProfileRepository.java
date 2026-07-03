package com.accountmanagement.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

    Optional<UserProfile> findByUserId(String userName);

}
