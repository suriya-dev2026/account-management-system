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
import com.accountmanagement.constants.message.VbsYearMessage;
import com.accountmanagement.model.VbsYear;
import com.accountmanagement.request.VbsYearRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.VbsYearService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/vbs/year")
@Tag(name = "VbsYearController")
public class VbsYearController {

    private final VbsYearService vbsYearService;

    public VbsYearController(VbsYearService vbsYearService) {
        this.vbsYearService = vbsYearService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addVbsYear(@Valid @RequestBody VbsYearRequest vbsYearRequest) {
        vbsYearRequest.sanitizeInput();
        vbsYearService.createVbsYear(vbsYearRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsYearMessage.ADD_VBS_YEAR, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateVbsYear(@PathVariable Integer id,
            @Valid @RequestBody VbsYearRequest vbsYearRequest) {
        vbsYearService.updateVbsYear(id, vbsYearRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsYearMessage.UPDATE_VBS_YEAR, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteVbsYearById(@PathVariable Integer id) {
        vbsYearService.deleteVbsYearById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsYearMessage.DELETE_VBS_YEAR, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllVbsYears() {
        List<VbsYear> vbsYears = vbsYearService.viewAllVbsYears();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsYearMessage.VIEW_ALL_VBS_YEARS, 200);
        response.setData(vbsYears);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
