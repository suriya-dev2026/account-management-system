package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.SubscriptionUsage;

public interface SubscriptionUsageRepository extends JpaRepository<SubscriptionUsage, UUID> {

}
