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
import com.accountmanagement.constants.message.StaffAttendanceMessage;
import com.accountmanagement.model.StaffAttendance;
import com.accountmanagement.request.StaffAttendanceRequest;
import com.accountmanagement.request.StaffAttendanceUpdateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.StaffAttendanceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/staff/attendance")
public class StaffAttendanceController {

    private final StaffAttendanceService staffAttendanceService;

    public StaffAttendanceController(StaffAttendanceService staffAttendanceService) {
        this.staffAttendanceService = staffAttendanceService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createStaffAttedance(
            @Valid @RequestBody StaffAttendanceRequest staffAttendanceRequest) {
        staffAttendanceService.createStaffAttendance(staffAttendanceRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, StaffAttendanceMessage.ADD_STAFF_ATTENDANCE, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateStaffAttedance(@PathVariable UUID id,
            @Valid @RequestBody StaffAttendanceUpdateRequest staffAttendanceUpdateRequest) {
        staffAttendanceService.updateStaffAttendance(id, staffAttendanceUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, StaffAttendanceMessage.UPDATE_STAFF_ATTENDANCE,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteStaffAttedanceById(@PathVariable UUID id) {
        staffAttendanceService.deleteStaffAttendanceById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, StaffAttendanceMessage.DELETE_STAFF_ATTENDANCE,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllStaffAttedance() {
        List<StaffAttendance> staffAttendance = staffAttendanceService.viewAllStaffAttendances();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, StaffAttendanceMessage.VIEW_ALL_STAFF_ATTENDANCE,
                200);
        response.setData(staffAttendance);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
