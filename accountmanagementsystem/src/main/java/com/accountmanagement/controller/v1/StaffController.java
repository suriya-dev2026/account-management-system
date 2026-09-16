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
import com.accountmanagement.constants.message.StaffMessage;
import com.accountmanagement.model.Staff;
import com.accountmanagement.request.StaffRequest;
import com.accountmanagement.request.StaffUpdateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.StaffService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/staff")
@Tag(name = "StaffController")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createStaff(@Valid @RequestBody StaffRequest staffRequest) {
        staffRequest.sanitizeInput();
        staffService.createStaff(staffRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, StaffMessage.ADD_STAFF, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateStaff(@PathVariable UUID id,
            @Valid @RequestBody StaffUpdateRequest staffUpdateRequest) {
        staffUpdateRequest.sanitizeInput();
        staffService.updateStaff(id, staffUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, StaffMessage.UPDATE_STAFF, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteStaffById(@PathVariable UUID id) {
        staffService.deleteStaffById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, StaffMessage.DELETE_STAFF, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllStaff() {
        List<Staff> staff = staffService.viewAllStaffs();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, StaffMessage.VIEW_ALL_STAFFS, 200);
        response.setData(staff);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
