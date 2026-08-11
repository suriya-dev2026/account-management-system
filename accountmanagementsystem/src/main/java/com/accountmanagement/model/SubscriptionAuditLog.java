package com.accountmanagement.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.accountmanagement.enums.AuditLogAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "subscription_audit_logs")
@Data
public class SubscriptionAuditLog {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "old_plan_id")
    private UUID oldPlanId;

    @Column(name = "new_plan_id")
    private UUID newPlanId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private AuditLogAction action;

    @Column(name = "changed_by")
    private UUID changedBy;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
