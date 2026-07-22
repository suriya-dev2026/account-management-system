package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    boolean existsByCode(String organizationCode);

}
