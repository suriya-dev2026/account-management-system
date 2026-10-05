package com.accountmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import com.accountmanagement.model.listeners.AccountingBankAccountListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "accounting_bank_accounts")
@Data
@EntityListeners(AccountingBankAccountListener.class)
public class AccountingBankAccount {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "bank_account_type_id")
    private Integer bankAccountTypeId;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "branch")
    private String branch;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "ifsc")
    private String ifsc;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "opening_balance")
    private BigDecimal openingBalance;

    @Column(name = "current_balance")
    private BigDecimal currentBalanace;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
