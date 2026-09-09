package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.Staff;

public interface StaffRepository extends JpaRepository<Staff, UUID> {

    boolean existsByUserId(UUID userId);

}
