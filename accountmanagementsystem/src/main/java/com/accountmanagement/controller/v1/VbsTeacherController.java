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
import com.accountmanagement.constants.message.VbsTeacherMessage;
import com.accountmanagement.model.VbsTeacher;
import com.accountmanagement.request.VbsTeacherRequest;
import com.accountmanagement.request.VbsTeacherUpdateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.VbsTeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/vbs/teacher")
public class VbsTeacherController {

    private final VbsTeacherService vbsTeacherService;

    public VbsTeacherController(VbsTeacherService vbsTeacherService) {
        this.vbsTeacherService = vbsTeacherService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createVbsTeacher(@Valid @RequestBody VbsTeacherRequest vbsTeacherRequest) {
        vbsTeacherRequest.sanitizeInput();
        vbsTeacherService.createVbsTeacher(vbsTeacherRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsTeacherMessage.ADD_VBS_TEACHER, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateVbsTeacher(@PathVariable UUID id,
            @Valid @RequestBody VbsTeacherUpdateRequest vbsTeacherUpdateRequest) {
        vbsTeacherUpdateRequest.sanitizeInput();
        vbsTeacherService.updateVbsTeacher(id, vbsTeacherUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsTeacherMessage.UPDATE_VBS_TEACHER, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteVbsTeacherById(@PathVariable UUID id) {
        vbsTeacherService.deleteVbsTeacherById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsTeacherMessage.DELETE_VBS_TEACHER, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllVbsTeacher() {
        List<VbsTeacher> vbsTeacher = vbsTeacherService.viewAllVbsTeachers();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsTeacherMessage.VIEW_ALL_VBS_TEACHERS, 200);
        response.setData(vbsTeacher);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
