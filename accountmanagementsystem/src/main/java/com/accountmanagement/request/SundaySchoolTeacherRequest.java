package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;
import com.accountmanagement.validations.ValidClassId;
import com.accountmanagement.validations.ValidDate;
import com.accountmanagement.validations.ValidMemberId;
import com.accountmanagement.validations.ValidOrganizationId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SundaySchoolTeacherRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @ValidMemberId(message = "Member Id Does Not Exists")
    @NotNull(message = "Member Id Is Required")
    private UUID memberId;

    @ValidClassId(message = "Class Id Does Not Exists")
    @NotNull(message = "Class Id Is Required")
    private UUID classId;

    @ValidDate(message = "Invalid Date Format")
    @NotNull(message = "Date OF Join Is Required")
    private LocalDate dateOfJoin;

}
