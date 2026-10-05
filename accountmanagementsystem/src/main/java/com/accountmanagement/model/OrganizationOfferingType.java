package com.accountmanagement.model;

import java.time.LocalDateTime;
import java.util.UUID;
import com.accountmanagement.model.listeners.OrganizationOfferingTypeListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "organization_offering_types")
@Data
@EntityListeners(OrganizationOfferingTypeListener.class)
public class OrganizationOfferingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
