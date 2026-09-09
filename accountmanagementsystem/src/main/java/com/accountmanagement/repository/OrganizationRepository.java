package com.accountmanagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    boolean existsByNameAndCityId(String name, Integer cityId);

    boolean existsByRegistrationNumber(String registrationNumber);

    boolean existsByWebsite(String website);

    boolean existsByContactEmail(String email);

    Optional<Organization> findByContactEmail(String email);

    boolean existsByContactNumber(String primaryContactNumber);

}
