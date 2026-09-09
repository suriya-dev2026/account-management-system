package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidDate;
import com.accountmanagement.validations.ValidEndDate;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidOrganizationId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@ValidEndDate(message = "End Date must be Greater than or Equal to Start Date")
public class EventRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @NotBlank(message = "Event Name Is Required")
    @ValidInput(message = "Event Name Contains Invalid Characters")
    private String eventName;

    @NotBlank(message = "Event Type Is Required")
    @ValidInput(message = "Event Type Contains Invalid Characters")
    private String eventType;

    @NotNull(message = "Start Date Is Required")
    @ValidDate(message = "Invalid Start Date")
    private LocalDate startDate;

    @NotNull(message = "End Date Is Required")
    private LocalDate endDate;

    @NotBlank(message = "Location Is Required")
    @ValidInput(message = "Location Contains Invalid Character")
    private String location;

    @ValidInput(message = "Description Contains Invalid Character")
    private String description;

    public void sanitizeInput() {
        setEventName(Apputility.sanitizeInput(getEventName()));
        setEventType(Apputility.sanitizeInput(getEventType()));
        setLocation(Apputility.sanitizeInput(getLocation()));
        setDescription(Apputility.sanitizeInput(getDescription()));
    }

}
