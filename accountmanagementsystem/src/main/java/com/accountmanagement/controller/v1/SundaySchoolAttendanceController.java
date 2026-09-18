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
import com.accountmanagement.constants.message.SundaySchoolAttendanceMessage;
import com.accountmanagement.model.SundaySchoolAttendance;
import com.accountmanagement.request.AttendanceUpdateRequest;
import com.accountmanagement.request.SundaySchoolAttendanceRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.SundaySchoolAttendanceService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/sunday/school/attendance")
@Tag(name = "SundayAttendanceController")
public class SundaySchoolAttendanceController {

    private final SundaySchoolAttendanceService sundaySchoolAttendanceService;

    public SundaySchoolAttendanceController(SundaySchoolAttendanceService sundaySchoolAttendanceService) {
        this.sundaySchoolAttendanceService = sundaySchoolAttendanceService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addSundaySchoolAttendance(
            @Valid @RequestBody SundaySchoolAttendanceRequest sundaySchoolAttendanceRequest) {
        sundaySchoolAttendanceService.saveAttendance(sundaySchoolAttendanceRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolAttendanceMessage.ADD_SUNDAY_SCHOOL_ATTENDANCE, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateSundaySchoolAttendance(@PathVariable UUID id,
            @Valid @RequestBody AttendanceUpdateRequest request) {
        sundaySchoolAttendanceService.updateSundaySchoolAttendance(id, request);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolAttendanceMessage.UPDATE_SUNDAY_SCHOOL_ATTENDANCE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteSundaySchoolAttendanceById(@PathVariable UUID id) {
        sundaySchoolAttendanceService.deleteSundaySchoolAttendanceById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolAttendanceMessage.DELETE_SUNDAY_SCHOOL_ATTENDANCE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllSundaySchoolAttendances() {
        List<SundaySchoolAttendance> sundaySchoolAttendance = sundaySchoolAttendanceService
                .viewAllSundaySchoolAttendance();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolAttendanceMessage.VIEW_ALL_SUNDAY_SCHOOL_ATTENDANCE, 200);
        response.setData(sundaySchoolAttendance);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
