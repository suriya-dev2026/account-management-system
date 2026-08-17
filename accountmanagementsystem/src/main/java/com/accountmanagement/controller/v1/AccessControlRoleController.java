package com.accountmanagement.controller.v1;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.AccessControlRoleMessage;
import com.accountmanagement.model.AccessControlRole;
import com.accountmanagement.request.AccessControlRoleRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.AccessControlRoleService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/access/control/role")
@Tag(name = "AccessControlRoleController")
public class AccessControlRoleController {

    private final AccessControlRoleService accessControlRoleService;

    AccessControlRoleController(AccessControlRoleService accessControlRoleService) {
        this.accessControlRoleService = accessControlRoleService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createRole(
            @Valid @RequestBody AccessControlRoleRequest accessControlRoleRequest) {
        accessControlRoleRequest.sanitizeInput();
        accessControlRoleService.createRole(accessControlRoleRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlRoleMessage.ADD_ROLE, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateRole(@PathVariable UUID id,
            @Valid @RequestBody AccessControlRoleRequest accessControlRoleRequest) {
        accessControlRoleRequest.sanitizeInput();
        accessControlRoleService.updateRole(id, accessControlRoleRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlRoleMessage.UPDATE_ROLE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deleteRoleById(@PathVariable UUID id) {
        accessControlRoleService.deleteById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlRoleMessage.DELETE_ROLE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> viewAllRoles() {
        List<AccessControlRole> accessControlRole = accessControlRoleService.viewAllAccessControlRole();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlRoleMessage.VIEW_ALL_ROLES, 200);
        response.setData(accessControlRole);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
