package com.accountmanagement.request;

import com.accountmanagement.validations.ValidPassword;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeTemporaryPasswordRequest {

    @NotBlank(message = "New Password is Required")
    @ValidPassword(message = "Password must contain uppercase, lowercase, number, special character and be 8-20 characters long")
    private String newPassword;

    @NotBlank(message = "Confirm Password is Required")
    private String confirmPassword;

    @NotBlank(message = "reset token cannot be blank")
    private String resetToken;

}
