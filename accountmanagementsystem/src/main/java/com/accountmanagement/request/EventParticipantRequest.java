package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidDate;
import com.accountmanagement.validations.ValidEventId;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidMemberId;
import com.accountmanagement.validations.ValidOrganizationId;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventParticipantRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id is Required")
    private UUID organizationId;

    @NotNull(message = "Event Id Is Required")
    @ValidEventId(message = "Event Id Does Not Exists")
    private UUID eventId;

    @ValidMemberId(message = "Member Id Does Not Exists")
    private UUID memberId;

    @NotBlank(message = "Full Name Is Required")
    @ValidInput(message = "Full Name Contains Invalid Character")
    private String fullName;

    @NotNull(message = "Date Of Birth is Required")
    @ValidDate(message = "Invalid Date. Date Of Birth Cannot Be Future Date")
    private LocalDate dateOfBirth;

    @ValidInput(message = "School Grade Contains Invalid Character")
    private String schoolGrade;

    @NotBlank(message = "Address Is Required")
    @ValidInput(message = "Address Contains Invalid Characters")
    private String address;

    @NotBlank(message = "Contact Number Is Required")
    @ValidInput(message = "Contact Number Contains Invalid Characters")
    private String contactNumber;

    @ValidInput(message = "Emergency Contact Name Contains Invalid Characters")
    private String emergencyContactName;

    @ValidInput(message = "Emergency Contact Number Contains Invalid Characters")
    private String emergencyContactNumber;

    public void sanitizeInput() {
        setFullName(Apputility.sanitizeInput(getFullName()));
        setSchoolGrade(Apputility.sanitizeInput(getSchoolGrade()));
        setAddress(Apputility.sanitizeInput(getAddress()));
        setContactNumber(Apputility.sanitizeInput(getContactNumber()));
        setEmergencyContactName(Apputility.sanitizeInput(getEmergencyContactName()));
        setEmergencyContactNumber(Apputility.sanitizeInput(getEmergencyContactNumber()));
    }

}
