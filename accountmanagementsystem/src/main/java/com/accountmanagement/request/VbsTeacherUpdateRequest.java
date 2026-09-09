package com.accountmanagement.request;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import lombok.Data;

@Data
public class VbsTeacherUpdateRequest {

    @ValidInput(message = "Interest Area Contains Invalid Characters")
    private String interestArea;

    @ValidInput(message = "Priority Contains Invalid Characters")
    private String priority;

    public void sanitizeInput() {
        setInterestArea(Apputility.sanitizeInput(getInterestArea()));
        setPriority(Apputility.sanitizeInput(getPriority()));
    }

}
