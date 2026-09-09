package com.accountmanagement.request;

import java.util.UUID;
import com.accountmanagement.enums.Gender;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidContactNumber;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidMemberId;
import com.accountmanagement.validations.ValidOrganizationId;
import com.accountmanagement.validations.ValidVbsClassId;
import com.accountmanagement.validations.ValidYearId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VbsStudentRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @ValidYearId(message = "Vbs Year Id Does Not Exists")
    @NotNull(message = "Year Id Required")
    private Integer vbsYearId;

    @ValidVbsClassId(message = "Vbs Class Id Does Not Exists")
    @NotNull(message = "Class Id Is Required")
    private Integer vbsClassId;

    @ValidMemberId(message = "Member Id Does Not Exists")
    private UUID memberId;

    @ValidInput(message = "Student Name Contains Invalid Characters")
    private String studentName;

    private Gender gender;

    @ValidInput(message = "Contact Number contains invalid characters")
    private String contactNumber;

    @NotBlank(message = "Please Enter Contact Number")
    @ValidContactNumber(message = "Please Enter A Valid Contact Number")
    @ValidInput(message = "Contact Number Contains Invalid Characters")
    private String emergencyContactNumber;

    @ValidInput(message = "Emergency Contact Person Contains Invalid Characters")
    private String emergencyContactPerson;

    @ValidInput(message = "Address Contains Invalid Characters")
    private String address;

    public void sanitizeInput() {
        setStudentName(Apputility.sanitizeInput(getStudentName()));
        setContactNumber(Apputility.sanitizeInput(getContactNumber()));
        setEmergencyContactNumber(Apputility.sanitizeInput(getEmergencyContactNumber()));
        setEmergencyContactPerson(Apputility.sanitizeInput(getEmergencyContactPerson()));
        setAddress(Apputility.sanitizeInput(getAddress()));
    }

}
