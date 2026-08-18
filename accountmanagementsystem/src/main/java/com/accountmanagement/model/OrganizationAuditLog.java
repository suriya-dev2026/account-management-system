package com.accountmanagement.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.accountmanagement.model.listeners.OrganizationAuditLogListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "organization_audit_log")
@EntityListeners(OrganizationAuditLogListener.class)
@Data
public class OrganizationAuditLog {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "organization_code")
    private String organizationCode;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "logged_time ")
    private LocalDateTime loggedTime;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_pk")
    private String entityPk;

    @Column(name = "action_name")
    private String actionName;

    @Column(name = "existing_value")
    private String existingValue;

    @Column(name = "updated_value")
    private String updatedValue;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "ip_address")
    private String ipAddress;
}
