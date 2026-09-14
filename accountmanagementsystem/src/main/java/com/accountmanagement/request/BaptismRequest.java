package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidMemberId;
import com.accountmanagement.validations.ValidOrganizationId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BaptismRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "")
    private UUID organizationId;

    @ValidMemberId(message = "Member Id Does Not Exists")
    @NotNull(message = "Member Id Is Required")
    private UUID memberId;

    @NotNull(message = "Baptism Date Is Required")
    private LocalDate baptismDate;

    @ValidInput(message = "Notes Contains Invalid Input")
    private String notes;

    public void sanitizeInput() {
        setNotes(Apputility.sanitizeInput(getNotes()));
    }
}
