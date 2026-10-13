package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StaffAttendanceRequest {

    @NotNull(message = "OrganizationId Is Required")
    private UUID organizationId;

    @NotNull(message = "Attendance Date Is Required")
    private LocalDate attendanceDate;

    @Valid
    @NotNull(message = "Attendance List Is Required")
    private List<StaffAttendanceList> attendanceList;

}
