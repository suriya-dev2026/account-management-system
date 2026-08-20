package com.accountmanagement.request;

import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidOrganizationId;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SundaySchoolClassRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @NotBlank(message = "Class Name Is Required")
    @ValidInput(message = "Class Name Contains Invalid Characters")
    private String className;

    @NotNull(message = "Class Number Is Required")
    private Integer classNumber;

    public void sanitizeInput() {
        setClassName(Apputility.sanitizeInput(getClassName()));
    }

}
