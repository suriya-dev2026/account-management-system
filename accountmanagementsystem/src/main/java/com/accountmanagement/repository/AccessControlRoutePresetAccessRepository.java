package com.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.AccessControlRoutePresetAccess;

public interface AccessControlRoutePresetAccessRepository
        extends JpaRepository<AccessControlRoutePresetAccess, Integer> {

}
