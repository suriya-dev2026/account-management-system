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
import com.accountmanagement.constants.message.SundaySchoolTeacherMessage;
import com.accountmanagement.model.SundaySchoolTeacher;
import com.accountmanagement.request.SundaySchoolTeacherRequest;
import com.accountmanagement.request.SundaySchoolTeacherUpdateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.SundaySchoolTeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/sunday/school/teacher")
public class SundaySchoolTeacherController {

    private final SundaySchoolTeacherService sundaySchoolTeacherService;

    public SundaySchoolTeacherController(SundaySchoolTeacherService sundaySchoolTeacherService) {
        this.sundaySchoolTeacherService = sundaySchoolTeacherService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addSundaySchoolTeacher(
            @Valid @RequestBody SundaySchoolTeacherRequest sundaySchoolTeacherRequest) {
        sundaySchoolTeacherService.addSundaySchoolTeacher(sundaySchoolTeacherRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolTeacherMessage.ADD_SUNDAY_SCHOOL_TEACHER,
                201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateSundaySchoolTeacher(@PathVariable UUID id,
            @Valid @RequestBody SundaySchoolTeacherUpdateRequest sundaySchoolTeacherUpdateRequest) {
        sundaySchoolTeacherService.updateSundaySchoolTeacher(id, sundaySchoolTeacherUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolTeacherMessage.UPDATE_SUNDAY_SCHOOL_TEACHER,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteSundaySchoolTeacherById(@PathVariable UUID id) {
        sundaySchoolTeacherService.deleteSundaySchoolTeacherById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolTeacherMessage.DELETE_SUNDAY_SCHOOL_TEACHER,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllSundaySchoolTeachers() {
        List<SundaySchoolTeacher> sundaySchoolTeacher = sundaySchoolTeacherService.viewAllSundaySchoolTeachers();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolTeacherMessage.VIEW_ALL_SUNDAY_SCHOOL_TEACHER,
                200);
        response.setData(sundaySchoolTeacher);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
