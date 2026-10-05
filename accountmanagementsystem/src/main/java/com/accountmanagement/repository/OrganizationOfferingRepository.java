package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.OrganizationOffering;

public interface OrganizationOfferingRepository extends JpaRepository<OrganizationOffering, UUID> {

}
