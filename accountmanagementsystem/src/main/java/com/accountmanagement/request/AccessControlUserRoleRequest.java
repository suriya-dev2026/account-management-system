package com.accountmanagement.request;

import java.util.UUID;
import com.accountmanagement.validations.ValidRoleId;
import com.accountmanagement.validations.ValidUserId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccessControlUserRoleRequest {

    @NotNull(message = "User Id Is Required")
    @ValidUserId(message = "User Id Does Not Exists")
    private UUID userId;

    @NotNull(message = "Role Id Is Required")
    @ValidRoleId(message = "Role Id Does Not Exists")
    private UUID roleId;

}
