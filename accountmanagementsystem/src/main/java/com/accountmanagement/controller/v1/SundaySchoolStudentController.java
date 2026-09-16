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
import com.accountmanagement.constants.message.SundaySchoolStudentMessage;
import com.accountmanagement.model.SundaySchoolStudent;
import com.accountmanagement.request.SundaySchoolStudentRequest;
import com.accountmanagement.request.SundaySchoolStudentUpdateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.SundaySchoolStudentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/sunday/school/student")
@Tag(name = "SundayStudentController")
public class SundaySchoolStudentController {

    private final SundaySchoolStudentService sundaySchoolStudentService;

    public SundaySchoolStudentController(SundaySchoolStudentService sundaySchoolStudentService) {
        this.sundaySchoolStudentService = sundaySchoolStudentService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addSundaySchoolStudent(
            @Valid @RequestBody SundaySchoolStudentRequest sundaySchoolStudentRequest) {
        sundaySchoolStudentRequest.sanitizeInput();
        sundaySchoolStudentService.addSundaySchoolStudent(sundaySchoolStudentRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolStudentMessage.ADD_SUNDAY_SCHOOL_STUDENT, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateSundaySchoolStudent(@PathVariable UUID id,
            @Valid @RequestBody SundaySchoolStudentUpdateRequest sundaySchoolStudentUpdateRequest) {
        sundaySchoolStudentUpdateRequest.sanitizeInput();
        sundaySchoolStudentService.updateSundaySchoolStudent(id, sundaySchoolStudentUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolStudentMessage.UPDATE_SUNDAY_SCHOOL_STUDENT, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteSundaySchoolStudentbyId(@PathVariable UUID id) {
        sundaySchoolStudentService.deleteSundaySchoolStudentById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolStudentMessage.DELETE_SUNDAY_SCHOOL_STUDENT, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllSundaySchoolStudent() {
        List<SundaySchoolStudent> sundaySchoolStudent = sundaySchoolStudentService.viewAllSundaySchoolStudent();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolStudentMessage.VIEW_ALL_SUNDAY_SCHOOL_STUDENT, 200);
        response.setData(sundaySchoolStudent);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
