package com.accountmanagement.request;

import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidOrganizationId;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrganizationOfferingTypeRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @NotNull(message = "Account Id Is Required")
    private UUID accountId;

    @NotBlank(message = "Type Name Is Required")
    @ValidInput(message = "Type Name Contains Invalid Characters")
    private String typeName;

    public void sanitizeInput() {
        setTypeName(Apputility.sanitizeInput(getTypeName()));
    }

}
