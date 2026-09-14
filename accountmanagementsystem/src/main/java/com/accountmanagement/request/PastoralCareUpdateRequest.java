package com.accountmanagement.request;

import java.time.LocalDate;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PastoralCareUpdateRequest {

    @ValidInput(message = "Visitor's Name Contains Invalid Characters")
    private String visitorName;

    @ValidInput(message = "Visitor's Contact Number Contains Invalid Characters")
    private String visitorContactNumber;

    @NotNull(message = "Visit Date Is Required")
    private LocalDate visitDate;

    @NotBlank(message = "Visit Type Is Required")
    @ValidInput(message = "Visit Type Contains Invalid Characters")
    private String visitType;

    @ValidInput(message = "Notes Contains Invalid Characters")
    private String notes;

    public void sanitizeInput() {
        setVisitorName(Apputility.sanitizeInput(getVisitorName()));
        setVisitorContactNumber(Apputility.sanitizeInput(getVisitorContactNumber()));
        setNotes(Apputility.sanitizeInput(getNotes()));
    }
}
