package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidContactNumber;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidMemberId;
import com.accountmanagement.validations.ValidOrganizationId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PastoralCareRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @ValidMemberId(message = "Member Id Does Not Exists")
    private UUID memberId;

    @ValidInput(message = "Visitor's Name Contains Invalid Characters")
    private String visitorName;

    @ValidInput(message = "Visitor's Contact Number Contains Invalid Characters")
    @ValidContactNumber(message = "Invalid Contact Number. Enter a valid 10 digit mobile number starting with 6,7,8 or 9")
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
        setVisitType(Apputility.sanitizeInput(getVisitType()));
        setVisitorContactNumber(visitorContactNumber);
        setNotes(Apputility.sanitizeInput(getNotes()));
    }
}
