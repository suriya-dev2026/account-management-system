package com.accountmanagement.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidOrganizationId;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountingBankTransactionRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    private UUID accountId;

    @NotNull(message = "Bank Account Id Is Required")
    private UUID bankAccountId;

    @NotNull(message = "Transaction Type Id Is Required")
    private Integer transactionTypeId;

    @ValidInput(message = "Reference Contains Invalid Characters")
    @Size(max = 100, message = "Reference must not exceed 100 characters")
    private String reference;

    @NotNull(message = "Amount Is Required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    @Digits(integer = 12, fraction = 2, message = "Amount must have maximum 12 digits and 2 decimal places")
    private BigDecimal amount;

    @NotNull(message = "Transaction Date Is Required")
    private LocalDate transactionDate;

    @ValidInput(message = "Remarks Contains Invalid Characters")
    @Size(max = 500, message = "Remarks must not exceed 500 characters")
    private String remarks;

    public void sanitizeInput() {
        setReference(Apputility.sanitizeInput(getReference()));
        setRemarks(Apputility.sanitizeInput(getRemarks()));
    }

}
