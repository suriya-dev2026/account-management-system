package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidOrganizationId;
import com.accountmanagement.validations.ValidStartDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BulletinRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @NotBlank(message = "Tittle Is Required")
    @ValidInput(message = "Title Contains Invalid Characters")
    @Size(max = 200, message = "Title Must Not Exceed 200 Characters")
    private String title;

    @ValidInput(message = "Content Contains Invalid Characters")
    private String content;

    @ValidStartDate(message = "Invalid Date")
    private LocalDate bulletinDate;

    @ValidStartDate(message = "Invalid Date")
    private LocalDate publishDate;

    @ValidStartDate(message = "Invalid Date")
    private LocalDate expiryDate;

    public void sanitizeInput() {
        setTitle(Apputility.sanitizeInput(getTitle()));
        setContent(Apputility.sanitizeInput(getContent()));
    }

}
