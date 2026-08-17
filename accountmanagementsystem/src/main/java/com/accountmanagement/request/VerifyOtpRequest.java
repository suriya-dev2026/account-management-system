
package com.accountmanagement.request;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidOtp;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyOtpRequest {

    @NotBlank(message = "Please enter email")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "otp is required")
    @ValidOtp(message = "otp must be 6 digits only")
    private String otp;

    public void sanitizeInput() {
        setEmail(Apputility.sanitizeInput(getEmail()));
        setOtp(Apputility.sanitizeInput(getOtp()));
    }
}
