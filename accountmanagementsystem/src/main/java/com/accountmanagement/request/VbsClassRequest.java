package com.accountmanagement.request;

import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidOrganizationId;
import com.accountmanagement.validations.ValidYearId;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VbsClassRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @NotBlank(message = "Class Name Is Required")
    @ValidInput(message = "class Name Contains Invalid Characters")
    private String className;

    @NotNull(message = "Year Id Is Required")
    @ValidYearId(message = "Year Id Does Not Exists")
    private Integer yearId;

    private UUID teacherId;

    public void sanitizeInput() {
        setClassName(Apputility.sanitizeInput(getClassName()));
    }

}
