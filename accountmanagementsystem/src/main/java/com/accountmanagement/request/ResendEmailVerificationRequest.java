package com.accountmanagement.request;

import java.util.UUID;

import com.accountmanagement.validations.ValidOrganizationId;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResendEmailVerificationRequest {

    @NotNull(message = "Organization Id Is Required")
    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    private UUID organizationId;

    @Email(message = "Pleas Enter Valid Email")
    @NotBlank(message = "Email Is Required")
    private String email;

}
