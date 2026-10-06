package com.accountmanagement.request;

import java.util.UUID;

import com.accountmanagement.enums.Direction;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidAccountId;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidOrganizationId;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountingBankTransactionTypeRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @ValidInput(message = "Type Name Contains Invalid Characters")
    @NotBlank(message = "Type Name Is Required")
    private String typeName;

    @NotNull(message = "Account Id Is Required")
    @ValidAccountId(message = "Account Id Does Not Exists")
    private UUID accountId;

    private Direction direction;

    public void sanitizeInput() {
        setTypeName(Apputility.sanitizeInput(getTypeName()));
    }

}
