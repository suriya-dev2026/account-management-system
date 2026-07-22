package com.accountmanagement.request;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidContactNumber;
import com.accountmanagement.validations.ValidUserEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrganizationRequest {

        @NotBlank(message = "name is required")
        @Size(min = AppConstants.minNameLength, max = AppConstants.maxNameLength, message = "Name must be between "
                        + AppConstants.minNameLength + " and " + AppConstants.maxNameLength + " characters")
        private String name;

        @NotBlank(message = "registration number is required")
        @Size(min = AppConstants.minNameLength, max = AppConstants.maxNameLength, message = "Registration number must be between "
                        + AppConstants.minNameLength + " and " + AppConstants.maxNameLength + " characters")
        private String registrationNumber;

        @NotBlank(message = "please enter email")
        @Email(message = "invalid email provided")
        @ValidUserEmail(message = "Account with this email already exists")
        @Size(min = AppConstants.minNameLength, max = AppConstants.maxEmailLength, message = "email must be between "
                        + AppConstants.minNameLength + " and " + AppConstants.maxEmailLength + " characters")
        private String email;

        @NotBlank(message = "please enter phone number")
        @ValidContactNumber(message = "please enter a valid contact number")
        private String contactNumber;

        private String website;

        @NotBlank(message = "address is required")
        private String address;

        @NotBlank(message = "city is required")
        private String city;

        @NotBlank(message = "state is required")
        private String state;

        @NotBlank(message = "country is required")
        private String country;

        @NotBlank(message = "postal code is required")
        @Size(min = AppConstants.minPostalCodeLength, max = AppConstants.maxPostalCodeLength, message = "postal code must be between "
                        + AppConstants.minPostalCodeLength + " and " + AppConstants.maxPostalCodeLength + " characters")
        private String postalCode;

        @NotBlank(message = "primary contact name is required")
        private String primaryContactName;

        @NotBlank(message = "primary contact email is required")
        @Email(message = "invalid primary contact email")
        private String primaryContactEmail;

        @NotBlank(message = "primary contact phone is required")
        @ValidContactNumber(message = "please enter a valid phone number")
        private String primaryContactPhone;

        public void sanitizeInput() {
                setName(Apputility.sanitizeInput(getName()));
                setRegistrationNumber(Apputility.sanitizeInput(getRegistrationNumber()));
                setEmail((Apputility.sanitizeInput(getEmail())));
                setContactNumber(Apputility.sanitizeInput(getContactNumber()));
                setWebsite(Apputility.sanitizeInput(getWebsite()));
                setAddress(Apputility.sanitizeInput(getAddress()));
                setCity(Apputility.sanitizeInput(getCity()));
                setState(Apputility.sanitizeInput(getState()));
                setCountry(Apputility.sanitizeInput(getCountry()));
                setPostalCode(Apputility.sanitizeInput(getPostalCode()));
                setPrimaryContactName(Apputility.sanitizeInput(getPrimaryContactName()));
                setPrimaryContactEmail(Apputility.sanitizeInput(getPrimaryContactEmail()));
                setPrimaryContactPhone(Apputility.sanitizeInput(getPrimaryContactPhone()));

        }
}
