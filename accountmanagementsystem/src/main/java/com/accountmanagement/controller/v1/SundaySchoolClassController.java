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
import com.accountmanagement.constants.message.SundaySchoolClassMessage;
import com.accountmanagement.model.SundaySchoolClass;
import com.accountmanagement.request.SundaySchoolClassRequest;
import com.accountmanagement.request.SundaySchoolClassUpdateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.SundaySchoolClassService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/sunday/school/class")
@Tag(name = "SundayClassController")
public class SundaySchoolClassController {

    private final SundaySchoolClassService sundaySchoolClassService;

    public SundaySchoolClassController(SundaySchoolClassService sundaySchoolClassService) {
        this.sundaySchoolClassService = sundaySchoolClassService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addSundaySchoolClass(
            @Valid @RequestBody SundaySchoolClassRequest sundaySchoolClassRequest) {
        sundaySchoolClassRequest.sanitizeInput();
        sundaySchoolClassService.addSundaySchoolClass(sundaySchoolClassRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, SundaySchoolClassMessage.ADD_SUNDAY_SCHOOL_CLASS,
                201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateSundaySchoolClass(@PathVariable UUID id,
            @Valid @RequestBody SundaySchoolClassUpdateRequest sundaySchoolClassRequest) {
        sundaySchoolClassRequest.sanitizeInput();
        sundaySchoolClassService.updateSundaySchoolClass(id, sundaySchoolClassRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolClassMessage.UPDATE_SUNDAY_SCHOOL_CLASS,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteSundaySchoolClassById(@PathVariable UUID id) {
        sundaySchoolClassService.deleteSundaySchoolClassById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolClassMessage.DELETE_SUNDAY_SCHOOL_CLASS, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllSundaySchoolClass() {
        List<SundaySchoolClass> sundaySchoolClass = sundaySchoolClassService.viewAllSundaySchoolClasses();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolClassMessage.VIEW_ALL_SUNDAY_SCHOOL_CLASS, 200);
        response.setData(sundaySchoolClass);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
