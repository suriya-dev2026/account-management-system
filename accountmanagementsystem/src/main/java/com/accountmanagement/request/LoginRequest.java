package com.accountmanagement.request;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidLogin;
import com.accountmanagement.validations.ValidPassword;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "please provide either username or email or phone number")
    @ValidLogin
    private String login;

    @NotBlank(message = "Please enter password")
    @ValidPassword(message = "Password must contain uppercase,lowercase, numbers, special characters")
    @Size(min = AppConstants.minPasswordLength, message = "Password requires a minimum of "
            + AppConstants.minPasswordLength + " characters")
    @Size(max = AppConstants.maxPasswordLength, message = "Password cannot exceed a maximum of "
            + AppConstants.maxPasswordLength + " characters")
    private String password;

    public void sanitizeInput() {
        setLogin(Apputility.sanitizeInput(getLogin()));
    }

}
