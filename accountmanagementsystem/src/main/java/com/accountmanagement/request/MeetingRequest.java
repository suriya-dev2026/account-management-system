package com.accountmanagement.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidMeetingTypeId;
import com.accountmanagement.validations.ValidOrganizationId;
import com.accountmanagement.validations.ValidStartDate;
import com.accountmanagement.validations.ValidTime;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@ValidTime(message = "Please Enter a Valid Time")
public class MeetingRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @ValidMeetingTypeId(message = "Meeting Type Id Does Not Exists")
    @NotNull(message = "Meeting Type Id Is Required")
    private Integer meetingTypeId;

    @ValidStartDate(message = "Please Enter a Valid Date")
    @NotNull(message = "Meeting Date Is Required")
    private LocalDate meetingDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @ValidInput(message = "Description Contains Invalid Characters")
    private String description;

    public void sanitizeInput() {
        setDescription(Apputility.sanitizeInput(getDescription()));
    }

}
