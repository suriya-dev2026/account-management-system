package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.VbsClass;

public interface VbsClassRepository extends JpaRepository<VbsClass, Integer> {

    boolean existsByClassName(String className);

    boolean existsByClassNameAndTeacherId(String className, UUID teacherId);

}
