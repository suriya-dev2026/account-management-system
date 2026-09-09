package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.VbsStudent;

public interface VbsStudentRepository extends JpaRepository<VbsStudent, UUID>{

    boolean existsByContactNumber(String contactNumber);

}
