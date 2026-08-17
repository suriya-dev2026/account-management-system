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
import com.accountmanagement.constants.message.AccessControlModulePresetMessage;
import com.accountmanagement.model.AccessControlModulePreset;
import com.accountmanagement.request.AccessControlModulePresetRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.AccessControlModulePresetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/access/control/module/preset")
public class AccessControlModulePresetController {

    private final AccessControlModulePresetService accessControlModulePresetService;

    public AccessControlModulePresetController(AccessControlModulePresetService accessControlModulePresetService) {
        this.accessControlModulePresetService = accessControlModulePresetService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addAccessControlModulePreset(
            @Valid @RequestBody AccessControlModulePresetRequest accessControlModulePresetRequest) {
        accessControlModulePresetRequest.sanitizeInput();
        accessControlModulePresetService.addAccessControlModulePreset(accessControlModulePresetRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccessControlModulePresetMessage.ADD_ACCESS_CONTROL_MODULE_PRESET, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateAccessControlModulePreset(@PathVariable Integer id,
            @Valid @RequestBody AccessControlModulePresetRequest accessControlModulePresetRequest) {
        accessControlModulePresetRequest.sanitizeInput();
        accessControlModulePresetService.updateAccessControlModulePreset(id, accessControlModulePresetRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccessControlModulePresetMessage.UPDATE_ACCESS_CONTROL_MODULE_PRESET, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAccessControlModulePresetById(@PathVariable Integer id) {
        accessControlModulePresetService.deleteAccessControlModulePresetById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccessControlModulePresetMessage.DELETE_ACCESS_CONTROL_MODULE_PRESET, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> viewAllAccessControlModulePreset() {
        List<AccessControlModulePreset> accessControlModulePreset = accessControlModulePresetService
                .viewAllAccessControlModulePreset();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                AccessControlModulePresetMessage.VIEW_ALL_ACCESS_CONTROL_MODULE_PRESETS, 200);
        response.setData(accessControlModulePreset);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
