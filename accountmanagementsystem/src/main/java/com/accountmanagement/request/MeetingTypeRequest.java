package com.accountmanagement.request;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MeetingTypeRequest {

    @ValidInput(message = "Category Contains Invalid Characters")
    @NotBlank(message = "Category Is Required")
    private String category;

    @ValidInput(message = "Description Contains Invalid Characters")
    private String description;

    public void sanitizeInput() {
        setCategory(Apputility.sanitizeInput(getCategory()));
        setDescription(Apputility.sanitizeInput(getDescription()));
    }

}
