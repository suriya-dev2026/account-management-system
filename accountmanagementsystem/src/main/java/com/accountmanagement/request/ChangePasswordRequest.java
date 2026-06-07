package com.accountmanagement.request;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordRequest {

    @NotBlank(message = "reset token cannot be blank")
    private String resetToken;

    @NotBlank(message = "Password is Required")
    @ValidPassword(message = "Password must contain uppercase,lowercase, with a number, special character and be8-20 characters long")
    private String newPassword;

    @NotBlank(message = "Confirm Password is Required")
    @ValidPassword(message = "Confirm Password must contain uppercase,lowercase, with a number, special character and be8-20 characters long")
    private String confirmPassword;

    public void sanitizeInput() {
        setResetToken(Apputility.sanitizeInput(getResetToken()));
        setNewPassword(Apputility.sanitizeInput(getNewPassword()));
        setConfirmPassword(Apputility.sanitizeInput(getConfirmPassword()));
    }

}
