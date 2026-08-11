package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.SubscriptionPlanFeature;

public interface SubscriptionPlanFeatureRepository extends JpaRepository<SubscriptionPlanFeature, UUID> {

    boolean existsByPlanIdAndFeatureId(UUID planId, UUID featureId);

}
