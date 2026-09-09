package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.enums.Gender;
import com.accountmanagement.enums.UserType;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidContactNumber;
import com.accountmanagement.validations.ValidDate;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidInputString;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationRequest {

        @NotNull(message = "organization id is required")
        private UUID organizationId;

        @NotBlank(message = "user name mandatory")
        @Size(min = AppConstants.minNameLength, max = AppConstants.maxNameLength, message = "Username must be between "
                        + AppConstants.minNameLength + " and " + AppConstants.maxNameLength + " characters")
        @ValidInputString(message = "user name should contain lowercase and number only avoid special character and upper case", allowNull = false, alphaOnly = false)
        @ValidInput(message = "Input contains invalid characters")
        private String userName;

        @Email(message = "invalid email provided")
        @Size(min = AppConstants.minNameLength, max = AppConstants.maxEmailLength, message = "email must be between "
                        + AppConstants.minNameLength + " and " + AppConstants.maxEmailLength + "characters")
        @ValidInput(message = "Input contains invalid characters")
        private String email;

        @NotBlank(message = "please enter contact number")
        @ValidContactNumber(message = "please enter a valid contact number")
        @ValidInput(message = "Input contains invalid characters")
        private String contactNumber;

        // @ValidPassword(message = "Password must contain uppercase, lowercase, number,
        // special character and be 8-20 characters long")
        // private String password;

        @NotBlank(message = "Please enter first name")
        @Size(min = AppConstants.minNameLength, max = AppConstants.maxNameLength, message = "first name must be between "
                        + AppConstants.minNameLength + " and " + AppConstants.maxNameLength + " characters")
        @ValidInputString(message = "first name should contain alphabets only ", allowNull = false, alphaOnly = true)
        @ValidInput(message = "Input contains invalid characters")
        private String firstName;

        @Size(min = AppConstants.minNameLength, max = AppConstants.maxNameLength, message = "last name must be between "
                        + AppConstants.minNameLength + " and " + AppConstants.maxNameLength + " characters")
        @ValidInputString(message = "last name should contain alphabets only ", allowNull = true, alphaOnly = true)
        @ValidInput(message = "Input contains invalid characters")
        private String lastName;

        @NotNull(message = "date of birth is required")
        @ValidDate(message = "please enter valid date")
        private LocalDate dateOfBirth;

        @NotNull(message = "please enter gender")
        private Gender gender;

        @ValidInput(message = "Input contains invalid characters")
        @NotBlank(message = "address is required")
        private String address;

        @NotNull(message = "UserType Cannot Be Blank")
        private UserType userType;

        public void sanitizeInput() {
                setUserName(Apputility.sanitizeInput(getUserName()));
                setFirstName(Apputility.sanitizeInput(getFirstName()));
                setLastName(Apputility.sanitizeInput(getLastName()));
                setAddress(Apputility.sanitizeInput(getAddress()));
        }

}
