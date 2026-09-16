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
import com.accountmanagement.constants.message.VbsStudentMessage;
import com.accountmanagement.model.VbsStudent;
import com.accountmanagement.request.VbsStudentRequest;
import com.accountmanagement.request.VbsStudentUpdateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.VbsStudentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/vbs/student")
@Tag(name = "VbsStudentController")
public class VbsStudentController {

    private final VbsStudentService vbsStudentService;

    public VbsStudentController(VbsStudentService vbsStudentService) {
        this.vbsStudentService = vbsStudentService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addVbsStudent(@Valid @RequestBody VbsStudentRequest vbsStudentRequest) {
        vbsStudentRequest.sanitizeInput();
        vbsStudentService.createVbsStudent(vbsStudentRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsStudentMessage.ADD_VBS_STUDENT, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateVbsStudent(@PathVariable UUID id,
            @Valid @RequestBody VbsStudentUpdateRequest vbsStudentUpdateRequest) {
        vbsStudentService.updateVbsStudent(id, vbsStudentUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsStudentMessage.UPDATE_VBS_STUDENT, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteVbsStudentById(@PathVariable UUID id) {
        vbsStudentService.deleteVbsStudentById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsStudentMessage.DELETE_VBS_STUDENT, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllVbsStudent() {
        List<VbsStudent> vbsStudent = vbsStudentService.viewAllStudents();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsStudentMessage.VIEW_ALL_VBS_STUDENTS, 200);
        response.setData(vbsStudent);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
