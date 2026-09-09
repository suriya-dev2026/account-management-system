package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;
import com.accountmanagement.enums.Gender;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidContactNumber;
import com.accountmanagement.validations.ValidDate;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidOrganizationId;
import com.accountmanagement.validations.ValidUserId;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StaffRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @ValidUserId(message = "User Id Does Not Exists")
    @NotNull(message = "User Id Is Required")
    private UUID userId;

    @NotBlank(message = "Staff Type Is Required")
    private String staffType;

    private UUID memberId;

    @ValidInput(message = "First Name Contains Invalid Characters")
    private String firstName;

    @ValidInput(message = "Last Name Contains Invalid Characters")
    private String lastName;

    private Gender gender;

    @ValidInput(message = "Qualification Contains Invalid Characters")
    private String qualification;

    @ValidInput(message = "Designation Contains Invalid Characters")
    private String designation;

    private Integer age;

    @ValidDate(message = "please enter a valid date. Birth date cannot be in the future")
    private LocalDate dateOfBirth;

    @ValidDate(message = "please enter a valid date. Join date cannot be in the future")
    private LocalDate dateOfJoin;

    @ValidContactNumber(message = "Invalid Contact Number. Enter a valid 10 digit mobile number starting with 6,7,8 or 9")
    private String contactNumber;

    private Boolean isWaterBaptised;

    private Boolean isSpiritBaptised;

    public void sanitizeInput() {
        setFirstName(Apputility.sanitizeInput(getFirstName()));
        setLastName(Apputility.sanitizeInput(getLastName()));
        setContactNumber(Apputility.sanitizeInput(getContactNumber()));
        setDesignation(Apputility.sanitizeInput(getDesignation()));
        setQualification(Apputility.sanitizeInput(getQualification()));
    }
}
