package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.SundaySchoolClass;

public interface SundaySchoolClassRepository extends JpaRepository<SundaySchoolClass, UUID> {

    boolean existsByClassName(String className);

    boolean existsByClassNumber(Integer classNumber);

}
