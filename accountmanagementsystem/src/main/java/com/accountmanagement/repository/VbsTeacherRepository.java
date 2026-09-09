package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.VbsTeacher;

public interface VbsTeacherRepository extends JpaRepository<VbsTeacher, UUID> {

}
