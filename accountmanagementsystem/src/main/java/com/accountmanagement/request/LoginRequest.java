package com.accountmanagement.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "username or email or phone number is required")
    private String userNameOrEmailOrPhone;

    @NotBlank(message = "Password is Required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$", message = "Password must contain uppercase,lowercase, number, special character and be8-20 characters long")
    private String password;

}
