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
import com.accountmanagement.constants.message.PastoralCareMessage;
import com.accountmanagement.model.PastoralCare;
import com.accountmanagement.request.PastoralCareRequest;
import com.accountmanagement.request.PastoralCareUpdateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.PastoralCareService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/pastoral/care")
@Tag(name = "PastoralCareController")
public class PastoralCareController {

    private final PastoralCareService pastoralCareService;

    public PastoralCareController(PastoralCareService pastoralCareService) {
        this.pastoralCareService = pastoralCareService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createPastoralCare(@Valid @RequestBody PastoralCareRequest pastoralCareRequest) {
        pastoralCareRequest.sanitizeInput();
        pastoralCareService.createPastoralCare(pastoralCareRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, PastoralCareMessage.ADD_PASTORAL_CARE, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updatePastoralCareById(@PathVariable UUID id,
            @Valid @RequestBody PastoralCareUpdateRequest pastoralCareUpdateRequest) {
        pastoralCareUpdateRequest.sanitizeInput();
        pastoralCareService.updatePastoralCare(id, pastoralCareUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, PastoralCareMessage.UPDATE_PASTORAL_CARE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deletePastoralCareById(@PathVariable UUID id) {
        pastoralCareService.deletePastoralCareById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, PastoralCareMessage.DELETE_PASTORAL_CARE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllPastoralCare() {
        List<PastoralCare> pastoralCare = pastoralCareService.viewAllPastoralCare();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, PastoralCareMessage.VIEW_ALL_PASTORAL_CARE, 200);
        response.setData(pastoralCare);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
