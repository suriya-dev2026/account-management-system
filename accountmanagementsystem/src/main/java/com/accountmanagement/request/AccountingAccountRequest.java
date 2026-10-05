package com.accountmanagement.request;

import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidOrganizationId;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountingAccountRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @NotBlank(message = "Account Name Is Required")
    @ValidInput(message = "Account Name Contains Invalid Characters")
    private String accountName;

    @NotNull(message = "AccountType Id Is Required")
    private Integer accountTypeId;

    private UUID parentAccountId;

    public void sanitizeInput() {
        setAccountName(Apputility.sanitizeInput(getAccountName()));
    }

}
