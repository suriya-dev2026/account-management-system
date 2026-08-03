package com.accountmanagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.OrganizationSetting;

public interface OrganizationSettingRepository extends JpaRepository<OrganizationSetting, UUID> {

    Optional<OrganizationSetting> findByOrganizationId(UUID id);

}
