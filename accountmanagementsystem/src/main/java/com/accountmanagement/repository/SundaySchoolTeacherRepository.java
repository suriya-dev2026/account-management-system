package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.SundaySchoolTeacher;

public interface SundaySchoolTeacherRepository extends JpaRepository<SundaySchoolTeacher, UUID> {

    boolean existsByMemberIdAndClassId(UUID memberId, UUID classId);

}
