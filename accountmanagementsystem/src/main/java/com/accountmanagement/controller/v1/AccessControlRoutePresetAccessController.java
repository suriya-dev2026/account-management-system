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
import com.accountmanagement.constants.message.AccessControlRoutePresetAccessMessage;
import com.accountmanagement.model.AccessControlRoutePresetAccess;
import com.accountmanagement.request.AccessControlRoutePresetAccessRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.AccessControlRoutePresetAccessService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/access/control/route/preset/access")
@Tag(name = "AccessControlRoutePresetAccessController")
public class AccessControlRoutePresetAccessController {

        private final AccessControlRoutePresetAccessService accessControlRoutePresetAccessService;

        public AccessControlRoutePresetAccessController(
                        AccessControlRoutePresetAccessService accessControlRoutePresetAccessService) {
                this.accessControlRoutePresetAccessService = accessControlRoutePresetAccessService;
        }

        @PostMapping("/add")
        public ResponseEntity<ApiResponse> addAccessControlRoutePresetAccess(
                        @Valid @RequestBody AccessControlRoutePresetAccessRequest accessControlRoutePresetAccessRequest) {
                accessControlRoutePresetAccessService
                                .addAccessControlRoutePresetAccess(accessControlRoutePresetAccessRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlRoutePresetAccessMessage.ADD_ROUTE_PRESET_ACCESS, 201);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PutMapping("/update/{id}")
        public ResponseEntity<ApiResponse> updateAccessControlRoutePresetAccess(@PathVariable Integer id,
                        @Valid @RequestBody AccessControlRoutePresetAccessRequest accessControlRoutePresetAccessRequest) {
                accessControlRoutePresetAccessService.updateAccessControlRoutePresetAccess(id,
                                accessControlRoutePresetAccessRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlRoutePresetAccessMessage.UPDATE_ROUTE_PRESET_ACCESS, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<ApiResponse> deleteAccessControlRoutePresetAccessbyId(@PathVariable Integer id) {
                accessControlRoutePresetAccessService.deleteAccessControlRoutePresetAccessById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlRoutePresetAccessMessage.DELETE_ROUTE_PRESET_ACCESS, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("")
        public ResponseEntity<ApiResponse> viewAllAccessControlRoutePresetAccess() {
                List<AccessControlRoutePresetAccess> accessControlRoutePresetAccess = accessControlRoutePresetAccessService
                                .viewAllAccessControlRoutePresetAccess();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlRoutePresetAccessMessage.VIEW_ALL_ROUTE_PRESET_ACCESS, 200);
                response.setData(accessControlRoutePresetAccess);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }
}
