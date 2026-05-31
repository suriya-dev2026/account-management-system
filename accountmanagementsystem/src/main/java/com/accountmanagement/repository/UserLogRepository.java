package com.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accountmanagement.model.UserLog;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog, String> {

}
