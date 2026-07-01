package com.accountmanagement.request;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidUserEmail;
import com.accountmanagement.validations.ValidInputString;
import com.accountmanagement.validations.ValidPassword;
import com.accountmanagement.validations.ValidPhone;
import com.accountmanagement.validations.ValidUserPhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationRequest {

        @NotBlank(message = "Please enter first name")
        @Size(min = AppConstants.minNameLength, message = "First Name requires minimum of " + AppConstants.minNameLength
                        + " characters")
        @Size(max = AppConstants.maxNameLength, message = "First Name cannot exceed maximum of "
                        + AppConstants.maxNameLength + " characters")
        @ValidInputString(message = "first name should contain alphabets only ", allowNull = false, alphaOnly = true)
        private String firstName;

        @Size(min = AppConstants.minNameLength, message = "Last Name requires minimum of " + AppConstants.minNameLength
                        + " characters")
        @Size(max = AppConstants.maxNameLength, message = "Last Name cannot exceed maximum of "
                        + AppConstants.maxNameLength
                        + " characters")
        @ValidInputString(message = "last name should contain alphabets only ", allowNull = true, alphaOnly = true)
        private String lastName;

        @NotBlank(message = "please enter user name")
        @Size(min = AppConstants.minNameLength, message = "User Name requires minimum of " + AppConstants.minNameLength
                        + " characters")
        @Size(max = AppConstants.maxNameLength, message = "User Name cannot exceed maximum of "
                        + AppConstants.maxNameLength + " characters")
        @ValidInputString(message = "user name should contain lowercase and number only avoid special character and upper case", allowNull = false, alphaOnly = false)
        private String userName;

        @NotBlank(message = "please enter email")
        @Email(message = "invalid email provided")
        @ValidUserEmail(message = "Account with this email already exists")
        @Size(min = AppConstants.minNameLength, message = "email requires minimum of " + AppConstants.minNameLength
                        + " characters")
        @Size(max = AppConstants.emailLength, message = "User Name cannot exceed maximum of " + AppConstants.emailLength
                        + " characters")
        private String email;

        @NotBlank(message = "Please enter password")
        @ValidPassword(message = "Password must contain uppercase,lowercase, numbers, special characters")
        @Size(min = AppConstants.minPasswordLength, message = "Password requires a minimum of "
                        + AppConstants.minPasswordLength + " characters")
        @Size(max = AppConstants.maxPasswordLength, message = "Password cannot exceed a maximum of "
                        + AppConstants.maxPasswordLength + " characters")
        private String password;

        @NotBlank(message = "Please enter confirm password")
        @ValidPassword(message = "Password must contain uppercase,lowercase, numbers and special characters")
        @Size(min = AppConstants.minPasswordLength, message = "Password requires a minimum of "
                        + AppConstants.minPasswordLength + " characters")
        @Size(max = AppConstants.maxPasswordLength, message = "Password cannot exceed a maximum of "
                        + AppConstants.maxPasswordLength + " characters")
        private String confirmPassword;

        @NotBlank(message = "please enter phone number")
        @ValidPhone(message = "please enter a valid phone number")
        @ValidUserPhoneNumber(message = "Account with this phone number already exists")
        private String phone;

        @NotBlank(message = "please enter role")
        private String role;

        private String userId;

        @NotBlank(message = "address cannot be blank")
        private String address;

        public void sanitizeInput() {
                setFirstName(Apputility.sanitizeInput(getFirstName()));
                setLastName(Apputility.sanitizeInput(getLastName()));
                setUserName(Apputility.sanitizeInput(getUserName()));
                setEmail(Apputility.sanitizeInput(getEmail()));
                setPassword(Apputility.sanitizeInput(getPassword()));
                setPhone(Apputility.sanitizeInput(getPhone()));
                setAddress(Apputility.sanitizeInput(getAddress()));
        }

}
