package com.accountmanagement.controller.v1;

import com.accountmanagement.model.Certificate;
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
import com.accountmanagement.constants.message.CertificateMessage;
import com.accountmanagement.request.CertificateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.CertificateService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/certificate")
@Tag(name = "CertificateController")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCertificate(@Valid @RequestBody CertificateRequest certificateRequest) {
        certificateRequest.sanitizeInput();
        certificateService.createCertificate(certificateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, CertificateMessage.CREATE_CERTIFICATE, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCertificate(@PathVariable UUID id,
            @Valid @RequestBody CertificateRequest certificateRequest) {
        certificateRequest.sanitizeInput();
        certificateService.updateCertificateById(id, certificateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, CertificateMessage.UPDATE_CERTIFICATE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCertificateById(@PathVariable UUID id) {
        certificateService.deleteCertificateById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, CertificateMessage.DELETE_CERTIFICATE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllCertificates() {
        List<Certificate> certificate = certificateService.viewAllCertificates();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, CertificateMessage.VIEW_ALL_CERTIFICATES, 200);
        response.setData(certificate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
