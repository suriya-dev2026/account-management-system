package com.accountmanagement.request;

import com.accountmanagement.validations.ValidPhone;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    private Integer id;

    @NotBlank(message = "First Name Required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "first name should contain only alphabets")
    @Size(min = 3, max = 50, message = "First name must be between 3 to 50 characters")
    private String firstName;

    @Pattern(regexp = "^[A-Za-z ]*$", message = "last name should contain only alphabets")
    private String lastName;

    @NotBlank(message = "User Name Required")
    @Pattern(regexp = "^[a-z0-9 ]+$", message = "user name should contain only small letters and numbers")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(min = 5, max = 100, message = "Email should not exceed 100 characters")
    private String email;

    @NotBlank(message = "Password is Required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$", message = "Password must contain uppercase,lowercase, number, special character and be 8-20 characters long")
    private String password;

    @NotBlank(message = "Phone Number is Required")
    @ValidPhone(message = "phone number must be 10 digits only starts with 6,7,8 or 9 this digits")
    private String phone;

}
