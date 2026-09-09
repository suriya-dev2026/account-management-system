package com.accountmanagement.request;

import java.util.UUID;
import com.accountmanagement.validations.ValidUserId;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResendEmailVerificationRequest {

    @NotNull(message = "User Id Is Required")
    @ValidUserId(message = "User Id Does Not Exists")
    private UUID userId;

    @Email(message = "Please Enter Valid Email")
    @NotBlank(message = "Email Is Required")
    private String email;

}
