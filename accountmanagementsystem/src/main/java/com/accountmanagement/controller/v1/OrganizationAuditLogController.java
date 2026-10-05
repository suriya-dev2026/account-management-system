package com.accountmanagement.controller.v1;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.dto.OrganizationAuditLogDto;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.OrganizationAuditLogService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/v1/organization/audit/log")
@Tag(name = "OrganizationAuditLogController")
public class OrganizationAuditLogController {

    private final OrganizationAuditLogService organizationAuditLogService;

    public OrganizationAuditLogController(OrganizationAuditLogService organizationAuditLogService) {
        this.organizationAuditLogService = organizationAuditLogService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllOrganizationAuditLogs() {
        List<OrganizationAuditLogDto> log = organizationAuditLogService.getAllOrganizationLogs();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, "Logs Fetched Successfully", 200);
        response.setData(log);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
