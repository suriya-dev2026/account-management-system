package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;
import com.accountmanagement.validations.ValidOrganizationId;
import com.accountmanagement.validations.ValidVbsDate;
import com.accountmanagement.validations.ValidYear;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@ValidVbsDate(message = "Invalid Date")
public class VbsYearRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @ValidYear(message = "Invalid Year")
    @NotNull(message = "Year Is Required")
    private Integer year;

    @NotNull(message = "Start Date Is Required")
    private LocalDate startDate;

    @NotNull(message = "End Date Is Required")
    private LocalDate endDate;

}
