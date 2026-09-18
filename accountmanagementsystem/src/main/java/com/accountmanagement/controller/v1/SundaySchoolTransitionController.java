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
import com.accountmanagement.constants.message.SundaySchoolTransitionMessage;
import com.accountmanagement.model.SundaySchoolTransition;
import com.accountmanagement.request.SundaySchoolTransitionRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.SundaySchoolTransitionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/sunday/school/transition")
@Tag(name = "SundayTransitionController")
public class SundaySchoolTransitionController {

    private final SundaySchoolTransitionService sundaySchoolTransitionService;

    public SundaySchoolTransitionController(SundaySchoolTransitionService sundaySchoolTransitionService) {
        this.sundaySchoolTransitionService = sundaySchoolTransitionService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addSundaySchoolTransition(
            @Valid @RequestBody SundaySchoolTransitionRequest sundaySchoolTransitionRequest) {
        sundaySchoolTransitionService.addSundaySchoolTransition(sundaySchoolTransitionRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolTransitionMessage.ADD_SUNDAY_SCHOOL_TRANSITION, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateSundaySchoolTransition(@PathVariable UUID id,
            @Valid @RequestBody SundaySchoolTransitionRequest sundaySchoolTransitionRequest) {
        sundaySchoolTransitionService.updateSundaySchoolTransition(id, sundaySchoolTransitionRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolTransitionMessage.UPDATE_SUNDAY_SCHOOL_TRANSITION, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteSundaySchoolTransitionById(@PathVariable UUID id) {
        sundaySchoolTransitionService.deleteSundaySchoolTransitionById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolTransitionMessage.DELETE_SUNDAY_SCHOOL_TRANSITION, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllSundaySchoolTransition() {
        List<SundaySchoolTransition> sundaySchoolTransition = sundaySchoolTransitionService
                .viewAllSundaySchoolTransition();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SundaySchoolTransitionMessage.VIEW_ALL_SUNDAY_SCHOOL_TRANSITION, 200);
        response.setData(sundaySchoolTransition);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
