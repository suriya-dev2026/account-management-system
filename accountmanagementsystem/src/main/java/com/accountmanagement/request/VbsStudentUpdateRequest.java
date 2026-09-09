package com.accountmanagement.request;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidContactNumber;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidVbsClassId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VbsStudentUpdateRequest {

    @ValidVbsClassId(message = "Vbs Class Id Does Not Exists")
    @NotNull(message = "Class Id Is Required")
    private Integer vbsClassId;

    @NotBlank(message = "Please Enter Contact Number")
    @ValidContactNumber(message = "Please Enter A Valid Contact Number")
    @ValidInput(message = "Contact Number Contains Invalid Characters")
    private String emergencyContactNumber;

    @ValidInput(message = "Emergency Contact Person Contains Invalid Characters")
    private String emergencyContactPerson;

    public void sanitizeInput() {
        setEmergencyContactNumber(Apputility.sanitizeInput(getEmergencyContactNumber()));
        setEmergencyContactPerson(Apputility.sanitizeInput(getEmergencyContactPerson()));
    }
}
