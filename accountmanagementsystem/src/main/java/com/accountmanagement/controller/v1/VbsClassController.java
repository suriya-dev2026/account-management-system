package com.accountmanagement.controller.v1;

import java.util.List;

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
import com.accountmanagement.constants.message.VbsClassMessage;
import com.accountmanagement.model.VbsClass;
import com.accountmanagement.request.VbsClassRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.VbsClassService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/vbs/class")
public class VbsClassController {

    private final VbsClassService vbsClassService;

    public VbsClassController(VbsClassService vbsClassService) {
        this.vbsClassService = vbsClassService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addVbsClass(@Valid @RequestBody VbsClassRequest vbsClassRequest) {
        vbsClassRequest.sanitizeInput();
        vbsClassService.createVbsClass(vbsClassRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsClassMessage.ADD_VBS_CLASS, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateVbsClass(@PathVariable Integer id,
            @Valid @RequestBody VbsClassRequest vbsClassRequest) {
        vbsClassRequest.sanitizeInput();
        vbsClassService.updateVbsClass(id, vbsClassRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsClassMessage.UPDATE_VBS_CLASS, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteVbsClassById(@PathVariable Integer id) {
        vbsClassService.deleteVbsClassById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsClassMessage.DELETE_VBS_CLASS, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllVbsClass() {
        List<VbsClass> vbsClass = vbsClassService.viewAllVbsClass();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsClassMessage.VIEW_ALL_VBS_CLASSES, 200);
        response.setData(vbsClass);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
