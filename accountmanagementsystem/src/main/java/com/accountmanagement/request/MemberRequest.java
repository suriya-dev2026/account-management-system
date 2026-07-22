package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.enums.Gender;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidCategory;
import com.accountmanagement.validations.ValidContactNumber;
import com.accountmanagement.validations.ValidDate;
import com.accountmanagement.validations.ValidInputString;
import com.accountmanagement.validations.ValidLocation;
import com.accountmanagement.validations.ValidOrganizationCode;
import com.accountmanagement.validations.ValidOrganizationId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberRequest {

        @NotNull(message = "organization id required")
        @ValidOrganizationId(message = "organization id does not exists")
        private UUID organizationId;

        @ValidOrganizationCode(message = "invalid organization code")
        @NotBlank(message = "organization code is required")
        private String organizationCode;

        private UUID familyHeadId;

        @NotBlank(message = "category is required")
        @ValidCategory(message = "category does not exists")
        private String category;

        @NotBlank(message = "location is required")
        @ValidLocation(message = "location does not exists")
        private String location;

        private String relationship;

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
        @ValidInputString(message = "last name should contain alphabets only ", allowNull = false, alphaOnly = true)
        private String lastName;

        @NotBlank(message = "please enter user name")
        @Size(min = AppConstants.minNameLength, message = "User Name requires minimum of " + AppConstants.minNameLength
                        + " characters")
        @Size(max = AppConstants.maxNameLength, message = "User Name cannot exceed maximum of "
                        + AppConstants.maxNameLength + " characters")
        private String userName;

        @NotNull(message = "gender is required")
        private Gender gender;

        @NotNull(message = "please enter date of birth")
        @ValidDate(message = "pleases enter a valid date birth date cannot be in the future")
        private LocalDate dateOfBirth;

        @NotNull(message = "please enter date of join")
        @ValidDate(message = "pleases enter a valid date join date cannot be in the future")
        private LocalDate dateOfJoin;

        @ValidDate(message = "wedding date cannot be future")
        private LocalDate weddingDate;

        @NotBlank(message = "please enter email")
        @Email(message = "invalid email provided")
        @Size(min = AppConstants.minNameLength, message = "email requires minimum of " + AppConstants.minNameLength
                        + " characters")
        @Size(max = AppConstants.maxEmailLength, message = "email cannot exceed maximum of "
                        + AppConstants.maxEmailLength + " characters")
        private String email;

        @ValidContactNumber(message = "Invalid Contact Number. Enter a valid 10 digit mobile number starting with 6,7,8 or 9")
        private String contactNumber;

        @NotBlank(message = "address is required")
        private String address;

        private Boolean isWaterBaptised;

        private Boolean isSpiritBaptised;

        public void sanitizeInput() {
                setCategory(Apputility.sanitizeInput(getCategory()));
                setLocation(Apputility.sanitizeInput(getLocation()));
                setRelationship(Apputility.sanitizeInput(getRelationship()));
                setFirstName(Apputility.sanitizeInput(getFirstName()));
                setLastName(Apputility.sanitizeInput(getLastName()));
                setUserName(Apputility.sanitizeInput(getUserName()));
                setEmail(Apputility.sanitizeInput(getEmail()));
                setContactNumber(Apputility.sanitizeInput(getContactNumber()));
                setAddress(Apputility.sanitizeInput(getAddress()));
        }

}
