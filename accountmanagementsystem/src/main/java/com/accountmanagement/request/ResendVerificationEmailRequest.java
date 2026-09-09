package com.accountmanagement.request;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.validations.ValidInput;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResendVerificationEmailRequest {

    @NotBlank(message = "please enter email")
    @Email(message = "invalid email provided")
    @Size(min = AppConstants.minNameLength, max = AppConstants.maxEmailLength, message = "email must be between "
            + AppConstants.minNameLength + " and " + AppConstants.maxEmailLength + " characters")
    @ValidInput(message = "Input contains invalid characters")
    private String email;

}
