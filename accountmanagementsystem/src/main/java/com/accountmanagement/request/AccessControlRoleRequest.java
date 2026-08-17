package com.accountmanagement.request;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;

import lombok.Data;

@Data
public class AccessControlRoleRequest {

    @ValidInput(message = "role name contains invalid characters")
    private String roleName;

    @ValidInput(message = "description contains invalid characters")
    private String description;

    public void sanitizeInput() {
        setRoleName(Apputility.sanitizeInput(getRoleName()));
        setDescription(Apputility.sanitizeInput(getDescription()));
    }
}
