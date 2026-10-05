package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidMemberId;
import com.accountmanagement.validations.ValidOrganizationId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CertificateRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @ValidMemberId(message = "Member Id Does Not Exists")
    @NotNull(message = "Member Id Is Required")
    private UUID memberId;

    @NotBlank(message = "Certificate Type Is Required")
    @ValidInput(message = "Certificate Type Contains Invalid Characters")
    private String certificateType;

    @NotBlank(message = "Member Name Is Required")
    @ValidInput(message = "Member Name Contains Invalid Characters")
    private String memberName;

    @ValidInput(message = "Father Name Contains Invalid Characters")
    private String fatherName;

    @ValidInput(message = "Mother Name Contains Invalid Characters")
    private String motherName;

    @NotNull(message = "Issue Date Is Required")
    private LocalDate issueDate;

    private LocalDate eventDate;

    @ValidInput(message = "Issued By Contains Invalid Characters")
    private String issuedBy;

    public void sanitizeInput() {
        setMemberName(Apputility.sanitizeInput(getMemberName()));
        setFatherName(Apputility.sanitizeInput(getFatherName()));
        setMotherName(Apputility.sanitizeInput(getMotherName()));
        setCertificateType(Apputility.sanitizeInput(getCertificateType()));
        setIssuedBy(Apputility.sanitizeInput(getIssuedBy()));
    }
}
