package com.accountmanagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUserName(String userName);

    Optional<User> findByEmail(String email);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    boolean existsByContactNumber(String contactNumber);

    Optional<User> findByUserNameOrEmailOrContactNumber(String login, String login2, String login3);

    Optional<User> findByOrganizationId(UUID organizationId);

}
