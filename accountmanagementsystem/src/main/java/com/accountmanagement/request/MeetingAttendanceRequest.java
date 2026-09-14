package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import com.accountmanagement.validations.ValidDate;
import com.accountmanagement.validations.ValidMeeting;
import com.accountmanagement.validations.ValidOrganizationId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MeetingAttendanceRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @ValidMeeting(message = "Meeting Id Does Not Exists")
    @NotNull(message = "Meeting Id Is Required")
    private Integer meetingId;

    @ValidDate(message = "Please enter a valid date. Attendance date cannot be in the future")
    private LocalDate attendanceDate;

    @Valid
    @NotNull(message = "Attendance List Is Required")
    private List<MeetingAttendanceList> attendanceList;

}
