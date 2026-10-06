package com.accountmanagement.request;

import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidAccountId;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidOrganizationId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrganizationPaymentMethodRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @NotNull(message = "Account Id Is Required")
    @ValidAccountId(message = "Account Id Does Not Exists")
    private UUID accountId;

    @NotBlank(message = "Method Name Is Required")
    @ValidInput(message = "Method Name Contains Invalid Characters")
    private String methodName;

    @NotBlank(message = "Category Is Required")
    @ValidInput(message = "Category Contains Invalid Characters")
    private String category;

    public void sanitizeInput() {
        setMethodName(Apputility.sanitizeInput(getMethodName()));
        setCategory(Apputility.sanitizeInput(getCategory()));
    }
}
