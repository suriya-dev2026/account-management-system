package com.accountmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accountmanagement.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String userName);

    Optional<User> findByEmail(String email);

    User findByRefreshKey(String refreshKey);

    Optional<User> findByUserNameOrEmailOrPhone(String userName, String email, String phone);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

}
