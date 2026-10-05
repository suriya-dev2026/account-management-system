package com.accountmanagement.model;

import java.util.UUID;

import com.accountmanagement.model.listeners.AccountingBankAccountTypeListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "accounting_bank_account_types")
@Data
@EntityListeners(AccountingBankAccountTypeListener.class)
public class AccountingBankAccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "status")
    private String status;

}
