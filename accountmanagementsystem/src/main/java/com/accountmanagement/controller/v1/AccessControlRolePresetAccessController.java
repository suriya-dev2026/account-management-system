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
import com.accountmanagement.constants.message.AccessControlRolePresetAccessMessage;
import com.accountmanagement.model.AccessControlRolePresetAccess;
import com.accountmanagement.request.AccessControlRolePresetAccessRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.AccessControlRolePresetAccessService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/access/control/role/preset/access")
@Tag(name = "AccessControlrolePresetAccessController")
public class AccessControlRolePresetAccessController {

        private final AccessControlRolePresetAccessService accessControlRolePresetAccessService;

        public AccessControlRolePresetAccessController(
                        AccessControlRolePresetAccessService accessControlRolePresetAccessService) {
                this.accessControlRolePresetAccessService = accessControlRolePresetAccessService;
        }

        @PostMapping("/add")
        public ResponseEntity<ApiResponse> createAccessControlRolePresetAccess(
                        @Valid @RequestBody AccessControlRolePresetAccessRequest accessControlRolePresetAccessRequest) {
                accessControlRolePresetAccessService
                                .createAccessControlRolePresetAccess(accessControlRolePresetAccessRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlRolePresetAccessMessage.ADD_ACCESS_CONTROL_ROLE_PRESET_ACCESS, 201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/update/{id}")
        public ResponseEntity<ApiResponse> updateAccessControlRolePresetAccess(@PathVariable Integer id,
                        @Valid @RequestBody AccessControlRolePresetAccessRequest accessControlRolePresetAccessRequest) {
                accessControlRolePresetAccessService.updateAccessControlRolePresetAccess(id,
                                accessControlRolePresetAccessRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlRolePresetAccessMessage.UPDATE_ACCESS_CONTROL_ROLE_PRESET_ACCESS, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<ApiResponse> deleteAccessControlRolePresetAccessById(@PathVariable Integer id) {
                accessControlRolePresetAccessService.deleteAccessControlRolePresetAccessById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlRolePresetAccessMessage.DELETE_ACCESS_CONTROL_ROLE_PRESET_ACCESS, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("")
        public ResponseEntity<ApiResponse> viewAllAccessControlRolePresetAccess() {
                List<AccessControlRolePresetAccess> accessControlRolePresetAccess = accessControlRolePresetAccessService
                                .viewAllAccessControlRolePresetAccess();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlRolePresetAccessMessage.VIEW_ALL_ACCESS_CONTROL_ROLE_PRESET_ACCESS, 200);
                response.setData(accessControlRolePresetAccess);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

}
