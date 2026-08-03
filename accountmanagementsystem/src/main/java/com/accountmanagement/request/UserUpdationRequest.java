package com.accountmanagement.request;

import java.time.LocalDate;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidDate;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidInputString;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdationRequest {

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

        @ValidInput(message = "Input contains invalid characters")
        @NotBlank(message = "address is required")
        private String address;

        @NotBlank(message = "user type cannot be blank")
        @ValidInput(message = "Input contains invalid characters")
        private String userType;

        public void sanitizeInput() {
                setFirstName(Apputility.sanitizeInput(getFirstName()));
                setLastName(Apputility.sanitizeInput(getLastName()));
                setAddress(Apputility.sanitizeInput(getAddress()));
                setUserType(Apputility.sanitizeInput(getUserType()));
        }
}
