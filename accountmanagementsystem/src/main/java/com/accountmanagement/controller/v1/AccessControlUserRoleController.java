package com.accountmanagement.controller.v1;

import java.util.List;

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
import com.accountmanagement.constants.message.AccessControlUserRoleMessage;
import com.accountmanagement.model.AccessControlUserRole;
import com.accountmanagement.request.AccessControlUserRoleRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.AccessControlUserRoleService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/access/control/user/role")
@Tag(name = "AccessControlUserRoleController")
public class AccessControlUserRoleController {

    private final AccessControlUserRoleService accessControlUserRoleService;

    public AccessControlUserRoleController(AccessControlUserRoleService accessControlUserRoleService) {
        this.accessControlUserRoleService = accessControlUserRoleService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addAccessControlUserRole(
            @Valid @RequestBody AccessControlUserRoleRequest accessControlUserRoleRequest) {
        accessControlUserRoleService.addAccessControlUserRole(accessControlUserRoleRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlUserRoleMessage.ADD_USER_ROLE, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateAccessControlUserRole(@PathVariable Integer id,
            @Valid @RequestBody AccessControlUserRoleRequest accessControlUserRoleRequest) {
        accessControlUserRoleService.updateAccessControlUserRole(id, accessControlUserRoleRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlUserRoleMessage.UPDATE_USER_ROLE,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAccessControlUserRoleById(@PathVariable Integer id) {
        accessControlUserRoleService.deleteAccessControlUserRoleById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlUserRoleMessage.DELETE_USER_ROLE,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> viewAllAccessControlUser() {
        List<AccessControlUserRole> accessControlUserRoles = accessControlUserRoleService.viewAllAccessControlRole();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlUserRoleMessage.VIEW_ALL_USER_ROLES,
                200);
        response.setData(accessControlUserRoles);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
