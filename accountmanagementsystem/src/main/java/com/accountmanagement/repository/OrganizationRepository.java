package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    boolean existsByCode(String organizationCode);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    boolean existsByContactNumber(String contactNumber);

    boolean existsByRegistrationNumber(String registrationNumber);

    boolean existsByWebsite(String website);

}
