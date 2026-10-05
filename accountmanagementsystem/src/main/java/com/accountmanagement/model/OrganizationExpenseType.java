package com.accountmanagement.model;

import java.util.UUID;

import com.accountmanagement.model.listeners.OrganizationExpenseTypeListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "organization_expense_types")
@Data
@EntityListeners(OrganizationExpenseTypeListener.class)
public class OrganizationExpenseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "type_name")
    private String typeName;

    private String status;

}
