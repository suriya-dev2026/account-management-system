package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.SundaySchoolTransition;

public interface SundaySchoolTransitionRepository extends JpaRepository<SundaySchoolTransition, UUID> {

    Boolean existsByStudentIdAndFromClassIdAndToClassId(UUID studentId, UUID fromClassId, UUID toClassId);

}
