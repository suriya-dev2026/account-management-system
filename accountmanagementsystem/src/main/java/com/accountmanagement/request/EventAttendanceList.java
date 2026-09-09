package com.accountmanagement.request;

import java.util.UUID;
import com.accountmanagement.enums.AttendanceStatus;
import com.accountmanagement.validations.ValidEventParticipantId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventAttendanceList {

    @ValidEventParticipantId(message = "Event Participant Id Does Not Exists")
    @NotNull(message = "Event Participant Id Is Required")
    private UUID eventParticipantId;

    @NotNull(message = "Attendance Status Is Required")
    private AttendanceStatus attendanceStatus;

}
