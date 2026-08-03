package com.accountmanagement.model;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "subscription_plan_features")
@Data
public class SubscriptionPlanFeature {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "plan_id")
    private UUID planId;

    @Column(name = "feature_id")
    private UUID featureId;
}
