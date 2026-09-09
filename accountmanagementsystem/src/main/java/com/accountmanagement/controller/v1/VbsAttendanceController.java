
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
import com.accountmanagement.constants.message.VbsAttendanceMessage;
import com.accountmanagement.model.VbsAttendance;
import com.accountmanagement.request.AttendanceUpdateRequest;
import com.accountmanagement.request.VbsAttendanceRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.VbsAttendanceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/vbs/attendance")
public class VbsAttendanceController {
    private final VbsAttendanceService vbsAttendanceService;

    public VbsAttendanceController(VbsAttendanceService vbsAttendanceService) {
        this.vbsAttendanceService = vbsAttendanceService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createVbsAttendance(
            @Valid @RequestBody VbsAttendanceRequest vbsAttendanceRequest) {
        vbsAttendanceService.createVbsAttendance(vbsAttendanceRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsAttendanceMessage.ADD_VBS_ATTENDANCE, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateVbsAttendance(@PathVariable UUID id,
            @Valid @RequestBody AttendanceUpdateRequest vbsAttendanceUpdateRequest) {
        vbsAttendanceService.updateVbsAttendance(id, vbsAttendanceUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsAttendanceMessage.UPDATE_VBS_ATTENDANCE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteVbsAttendanceById(@PathVariable UUID id) {
        vbsAttendanceService.deleteVbsAttendanceById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsAttendanceMessage.DELETE_VBS_ATTENDANCE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllVbsAttendance() {
        List<VbsAttendance> vbsAttendance = vbsAttendanceService.viewAllVbsAttendance();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsAttendanceMessage.VIEW_ALL_VBS_ATTENDANCE, 200);
        response.setData(vbsAttendance);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
