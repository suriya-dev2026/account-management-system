package com.accountmanagement.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.SundaySchoolStudent;

public interface SundaySchoolStudentRepository extends JpaRepository<SundaySchoolStudent, UUID> {

}
