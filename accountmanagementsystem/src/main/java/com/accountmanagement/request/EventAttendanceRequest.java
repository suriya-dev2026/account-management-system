package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import com.accountmanagement.validations.ValidDate;
import com.accountmanagement.validations.ValidEventId;
import com.accountmanagement.validations.ValidOrganizationId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventAttendanceRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @ValidEventId(message = "Event Id Does Not Exists")
    @NotNull(message = "Event Id Is Required")
    private UUID eventId;

    @ValidDate(message = "Please enter a valid date. Attendance date cannot be in the future")
    private LocalDate attendanceDate;

    @Valid
    @NotNull(message = "Attendance List Is Required")
    private List<EventAttendanceList> attendanceList;

}
