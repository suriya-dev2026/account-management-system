package com.accountmanagement.request;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;

import lombok.Data;

@Data
public class StaffUpdateRequest {

    @ValidInput(message = "Qualification Contains Invalid Characters")
    private String qualification;

    @ValidInput(message = "Designation Contains Invalid Characters")
    private String designation;

    private Boolean isWaterBaptised;

    private Boolean isSpiritBaptised;

    public void sanitizeInput() {
        setQualification(Apputility.sanitizeInput(getQualification()));
        setDesignation(Apputility.sanitizeInput(getDesignation()));
    }

}
