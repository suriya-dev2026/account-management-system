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
import com.accountmanagement.constants.message.AccessControlModulePresetMessage;
import com.accountmanagement.constants.message.AccessControlRoleMessage;
import com.accountmanagement.constants.message.AccessControlRolePresetAccessMessage;
import com.accountmanagement.constants.message.AccessControlRouteMessage;
import com.accountmanagement.constants.message.AccessControlRoutePresetAccessMessage;
import com.accountmanagement.constants.message.AccessControlUserRoleMessage;
import com.accountmanagement.model.AccessControlModulePreset;
import com.accountmanagement.model.AccessControlRole;
import com.accountmanagement.model.AccessControlRolePresetAccess;
import com.accountmanagement.model.AccessControlRoute;
import com.accountmanagement.model.AccessControlRoutePresetAccess;
import com.accountmanagement.model.AccessControlUserRole;
import com.accountmanagement.request.AccessControlModulePresetRequest;
import com.accountmanagement.request.AccessControlRolePresetAccessRequest;
import com.accountmanagement.request.AccessControlRoleRequest;
import com.accountmanagement.request.AccessControlRoutePresetAccessRequest;
import com.accountmanagement.request.AccessControlRouteRequest;
import com.accountmanagement.request.AccessControlUserRoleRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.AccessControlModulePresetService;
import com.accountmanagement.service.AccessControlRolePresetAccessService;
import com.accountmanagement.service.AccessControlRoleService;
import com.accountmanagement.service.AccessControlRoutePresetAccessService;
import com.accountmanagement.service.AccessControlRouteService;
import com.accountmanagement.service.AccessControlUserRoleService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/access/control")
@Tag(name = "AccessControlController")
@RequiredArgsConstructor
public class AccessControlController {

        private final AccessControlRoleService accessControlRoleService;

        private final AccessControlModulePresetService accessControlModulePresetService;

        private final AccessControlRolePresetAccessService accessControlRolePresetAccessService;

        private final AccessControlRouteService accessControlRouteService;

        private final AccessControlRoutePresetAccessService accessControlRoutePresetAccessService;

        private final AccessControlUserRoleService accessControlUserRoleService;

        @PostMapping("/role/create")
        public ResponseEntity<ApiResponse> createRole(
                        @Valid @RequestBody AccessControlRoleRequest accessControlRoleRequest) {
                accessControlRoleRequest.sanitizeInput();
                accessControlRoleService.createRole(accessControlRoleRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlRoleMessage.ADD_ROLE, 201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/role/update/{id}")
        public ResponseEntity<ApiResponse> updateRole(@PathVariable UUID id,
                        @Valid @RequestBody AccessControlRoleRequest accessControlRoleRequest) {
                accessControlRoleRequest.sanitizeInput();
                accessControlRoleService.updateRole(id, accessControlRoleRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlRoleMessage.UPDATE_ROLE, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/role/delete/{id}")
        public ResponseEntity<ApiResponse> deleteRoleById(@PathVariable UUID id) {
                accessControlRoleService.deleteById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlRoleMessage.DELETE_ROLE, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/role/view")
        public ResponseEntity<ApiResponse> viewAllRoles() {
                List<AccessControlRole> accessControlRole = accessControlRoleService.viewAllAccessControlRole();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlRoleMessage.VIEW_ALL_ROLES,
                                200);
                response.setData(accessControlRole);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping("/module/preset/create")
        public ResponseEntity<ApiResponse> createAccessControlModulePreset(
                        @Valid @RequestBody AccessControlModulePresetRequest accessControlModulePresetRequest) {
                accessControlModulePresetRequest.sanitizeInput();
                accessControlModulePresetService.createAccessControlModulePreset(accessControlModulePresetRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlModulePresetMessage.ADD_ACCESS_CONTROL_MODULE_PRESET, 201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/module/preset/update/{id}")
        public ResponseEntity<ApiResponse> updateAccessControlModulePreset(@PathVariable Integer id,
                        @Valid @RequestBody AccessControlModulePresetRequest accessControlModulePresetRequest) {
                accessControlModulePresetRequest.sanitizeInput();
                accessControlModulePresetService.updateAccessControlModulePreset(id, accessControlModulePresetRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlModulePresetMessage.UPDATE_ACCESS_CONTROL_MODULE_PRESET, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/module/preset/delete/{id}")
        public ResponseEntity<ApiResponse> deleteAccessControlModulePresetById(@PathVariable Integer id) {
                accessControlModulePresetService.deleteAccessControlModulePresetById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlModulePresetMessage.DELETE_ACCESS_CONTROL_MODULE_PRESET, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/module/preset/view")
        public ResponseEntity<ApiResponse> viewAllAccessControlModulePreset() {
                List<AccessControlModulePreset> accessControlModulePreset = accessControlModulePresetService
                                .viewAllAccessControlModulePreset();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlModulePresetMessage.VIEW_ALL_ACCESS_CONTROL_MODULE_PRESETS, 200);
                response.setData(accessControlModulePreset);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping("/role/preset/create")
        public ResponseEntity<ApiResponse> createAccessControlRolePresetAccess(
                        @Valid @RequestBody AccessControlRolePresetAccessRequest accessControlRolePresetAccessRequest) {
                accessControlRolePresetAccessService
                                .createAccessControlRolePresetAccess(accessControlRolePresetAccessRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlRolePresetAccessMessage.ADD_ACCESS_CONTROL_ROLE_PRESET_ACCESS, 201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/role/preset/update/{id}")
        public ResponseEntity<ApiResponse> updateAccessControlRolePresetAccess(@PathVariable Integer id,
                        @Valid @RequestBody AccessControlRolePresetAccessRequest accessControlRolePresetAccessRequest) {
                accessControlRolePresetAccessService.updateAccessControlRolePresetAccess(id,
                                accessControlRolePresetAccessRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlRolePresetAccessMessage.UPDATE_ACCESS_CONTROL_ROLE_PRESET_ACCESS, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/role/preset/delete/{id}")
        public ResponseEntity<ApiResponse> deleteAccessControlRolePresetAccessById(@PathVariable Integer id) {
                accessControlRolePresetAccessService.deleteAccessControlRolePresetAccessById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlRolePresetAccessMessage.DELETE_ACCESS_CONTROL_ROLE_PRESET_ACCESS, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/role/preset/view")
        public ResponseEntity<ApiResponse> viewAllAccessControlRolePresetAccess() {
                List<AccessControlRolePresetAccess> accessControlRolePresetAccess = accessControlRolePresetAccessService
                                .viewAllAccessControlRolePresetAccess();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlRolePresetAccessMessage.VIEW_ALL_ACCESS_CONTROL_ROLE_PRESET_ACCESS, 200);
                response.setData(accessControlRolePresetAccess);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping("/route/create")
        public ResponseEntity<ApiResponse> createRoute(
                        @Valid @RequestBody AccessControlRouteRequest accessControlRouteRequest) {
                accessControlRouteRequest.sanitizeInput();
                accessControlRouteService.createRoute(accessControlRouteRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlRouteMessage.ADD_ROUTE, 201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/route/update/{id}")
        public ResponseEntity<ApiResponse> updateRoute(@PathVariable Integer id,
                        @Valid @RequestBody AccessControlRouteRequest accessControlRouteRequest) {
                accessControlRouteRequest.sanitizeInput();
                accessControlRouteService.updateRoute(id, accessControlRouteRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlRouteMessage.UPDATE_ROUTE,
                                200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/route/delete/{id}")
        public ResponseEntity<ApiResponse> deleteRouteById(@PathVariable Integer id) {
                accessControlRouteService.deleteById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlRouteMessage.DELETE_ROUTE,
                                200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/route/view")
        public ResponseEntity<ApiResponse> viewAllRoutes() {
                List<AccessControlRoute> accessControlRoute = accessControlRouteService.viewAllAccessControlRoutes();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlRouteMessage.VIEW_ALL_ROUTES,
                                200);
                response.setData(accessControlRoute);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping("/route/preset/create")
        public ResponseEntity<ApiResponse> addAccessControlRoutePresetAccess(
                        @Valid @RequestBody AccessControlRoutePresetAccessRequest accessControlRoutePresetAccessRequest) {
                accessControlRoutePresetAccessService
                                .addAccessControlRoutePresetAccess(accessControlRoutePresetAccessRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlRoutePresetAccessMessage.ADD_ROUTE_PRESET_ACCESS, 201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/route/preset/update/{id}")
        public ResponseEntity<ApiResponse> updateAccessControlRoutePresetAccess(@PathVariable Integer id,
                        @Valid @RequestBody AccessControlRoutePresetAccessRequest accessControlRoutePresetAccessRequest) {
                accessControlRoutePresetAccessService.updateAccessControlRoutePresetAccess(id,
                                accessControlRoutePresetAccessRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlRoutePresetAccessMessage.UPDATE_ROUTE_PRESET_ACCESS, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/route/preset/delete/{id}")
        public ResponseEntity<ApiResponse> deleteAccessControlRoutePresetAccessbyId(@PathVariable Integer id) {
                accessControlRoutePresetAccessService.deleteAccessControlRoutePresetAccessById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlRoutePresetAccessMessage.DELETE_ROUTE_PRESET_ACCESS, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/route/preset/view")
        public ResponseEntity<ApiResponse> viewAllAccessControlRoutePresetAccess() {
                List<AccessControlRoutePresetAccess> accessControlRoutePresetAccess = accessControlRoutePresetAccessService
                                .viewAllAccessControlRoutePresetAccess();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlRoutePresetAccessMessage.VIEW_ALL_ROUTE_PRESET_ACCESS, 200);
                response.setData(accessControlRoutePresetAccess);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping("/user/role/create")
        public ResponseEntity<ApiResponse> addAccessControlUserRole(
                        @Valid @RequestBody AccessControlUserRoleRequest accessControlUserRoleRequest) {
                accessControlUserRoleService.createAccessControlUserRole(accessControlUserRoleRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlUserRoleMessage.ADD_USER_ROLE,
                                201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/user/role/update/{id}")
        public ResponseEntity<ApiResponse> updateAccessControlUserRole(@PathVariable Integer id,
                        @Valid @RequestBody AccessControlUserRoleRequest accessControlUserRoleRequest) {
                accessControlUserRoleService.updateAccessControlUserRole(id, accessControlUserRoleRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlUserRoleMessage.UPDATE_USER_ROLE,
                                200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/user/role/delete/{id}")
        public ResponseEntity<ApiResponse> deleteAccessControlUserRoleById(@PathVariable Integer id) {
                accessControlUserRoleService.deleteAccessControlUserRoleById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlUserRoleMessage.DELETE_USER_ROLE,
                                200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/user/role/view")
        public ResponseEntity<ApiResponse> viewAllAccessControlUser() {
                List<AccessControlUserRole> accessControlUserRoles = accessControlUserRoleService
                                .viewAllAccessControlRole();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                AccessControlUserRoleMessage.VIEW_ALL_USER_ROLES,
                                200);
                response.setData(accessControlUserRoles);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

}
