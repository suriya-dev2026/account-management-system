package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.AccessControlUserRole;

public interface AccessControlUserRoleRepository extends JpaRepository<AccessControlUserRole, Integer> {

    boolean existsByUserId(UUID id);

}
