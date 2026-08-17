package com.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.AccessControlRoute;

public interface AccessControlRouteRepository extends JpaRepository<AccessControlRoute, Integer> {

    boolean existsByBackendRouteIgnoreCase(String trim);

    boolean existsByFrontendRouteIgnoreCase(String trim);

}
