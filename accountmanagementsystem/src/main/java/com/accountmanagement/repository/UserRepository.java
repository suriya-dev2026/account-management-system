package com.accountmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accountmanagement.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserNameOrEmail(String userName, String email);

    User findByUserName(String userName);

    Optional<User> findByEmail(String email);

}
