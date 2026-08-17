package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.AccessControlRole;

public interface AccessControlRoleRepository extends JpaRepository<AccessControlRole, UUID> {

    boolean existsByRoleNameIgnoreCase(String trim);

}
