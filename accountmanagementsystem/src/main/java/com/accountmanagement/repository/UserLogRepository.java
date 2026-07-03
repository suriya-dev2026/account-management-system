package com.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.UserLog;

public interface UserLogRepository extends JpaRepository<UserLog, String> {

}
