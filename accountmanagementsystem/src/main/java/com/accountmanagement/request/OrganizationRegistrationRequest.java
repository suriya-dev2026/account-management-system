package com.accountmanagement.request;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidContactNumber;
import com.accountmanagement.validations.ValidInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrganizationRegistrationRequest {

        @NotBlank(message = "name is required")
        @Size(min = AppConstants.minNameLength, max = AppConstants.maxNameLength, message = "Organization Name must be between "
                        + AppConstants.minNameLength + " and " + AppConstants.maxNameLength + " characters")
        @ValidInput(message = "Input contains invalid characters")
        private String name;

        @NotBlank(message = "registration number is required")
        @Size(min = AppConstants.minNameLength, max = AppConstants.maxNameLength, message = "Registration number must be between "
                        + AppConstants.minNameLength + " and " + AppConstants.maxNameLength + " characters")
        @ValidInput(message = "Input contains invalid characters")
        private String registrationNumber;

        @ValidInput(message = "Input contains invalid characters")
        private String website;

        @ValidInput(message = "Input contains invalid characters")
        @NotBlank(message = "address is required")
        private String address;

        @NotNull(message = "country is required")
        private Integer countryId;

        @NotNull(message = "state is required")
        private Integer stateId;

        @NotNull(message = "city is required")
        private Integer cityId;

        @Size(min = AppConstants.minPostalCodeLength, max = AppConstants.maxPostalCodeLength, message = "postal code must be between "
                        + AppConstants.minPostalCodeLength + " and " + AppConstants.maxPostalCodeLength + " characters")
        private String postalCode;

        @NotBlank(message = "primary contact name is required")
        @ValidInput(message = "Input contains invalid characters")
        private String primaryContactName;

        @NotBlank(message = "primary contact email is required")
        @Email(message = "invalid primary contact email")
        @ValidInput(message = "Input contains invalid characters")
        private String primaryContactEmail;

        @NotBlank(message = "primary contact number is required")
        @ValidContactNumber(message = "please enter a valid contact number")
        @ValidInput(message = "Input contains invalid characters")
        private String primaryContactNumber;

        @ValidInput(message = "input containts invalid characters")
        private String logoUrl;

        @ValidInput(message = "input containts invalid characters")
        private String faviconUrl;

        @ValidInput(message = "input containts invalid characters")
        private String primaryColor;

        @ValidInput(message = "input containts invalid characters")
        private String timeZone;

        @ValidInput(message = "input containts invalid characters")
        private String currency;

        @ValidInput(message = "input containts invalid characters")
        private String language;

        public void sanitizeInput() {
                setName(Apputility.sanitizeInput(getName()));
                setRegistrationNumber(Apputility.sanitizeInput(getRegistrationNumber()));
                setWebsite(Apputility.sanitizeInput(getWebsite()));
                setAddress(Apputility.sanitizeInput(getAddress()));
                setPostalCode(Apputility.sanitizeInput(getPostalCode()));
                setPrimaryContactName(Apputility.sanitizeInput(getPrimaryContactName()));
                setPrimaryContactEmail(Apputility.sanitizeInput(getPrimaryContactEmail()));
                setPrimaryContactNumber(Apputility.sanitizeInput(getPrimaryContactNumber()));
                setLogoUrl(Apputility.sanitizeInput(getLogoUrl()));
                setFaviconUrl(Apputility.sanitizeInput(getFaviconUrl()));
                setPrimaryColor(Apputility.sanitizeInput(getPrimaryColor()));
                setTimeZone(Apputility.sanitizeInput(getTimeZone()));
                setCurrency(Apputility.sanitizeInput(getCurrency()));
                setLanguage(Apputility.sanitizeInput(getLanguage()));
        }
}
