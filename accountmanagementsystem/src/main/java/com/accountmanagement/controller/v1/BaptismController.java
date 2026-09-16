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
import com.accountmanagement.constants.message.BaptismMessage;
import com.accountmanagement.model.Baptism;
import com.accountmanagement.request.BaptismRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.BaptismService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/baptism")
@Tag(name = "BaptismController")
public class BaptismController {

    private final BaptismService baptismService;

    public BaptismController(BaptismService baptismService) {
        this.baptismService = baptismService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createBaptism(@Valid @RequestBody BaptismRequest baptismRequest) {
        baptismRequest.sanitizeInput();
        baptismService.createBaptism(baptismRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, BaptismMessage.CREATE_BAPTISM, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateBaptismById(@PathVariable UUID id,
            @Valid @RequestBody BaptismRequest baptismRequest) {
        baptismRequest.sanitizeInput();
        baptismService.updateBaptism(id, baptismRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, BaptismMessage.UPDATE_BAPTISM, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBaptismById(@PathVariable UUID id) {
        baptismService.deleteBaptismById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, BaptismMessage.DELETE_BAPTISM, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllBaptism() {
        List<Baptism> baptism = baptismService.viewAllBaptism();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, BaptismMessage.VIEW_ALL_BAPTISM, 200);
        response.setData(baptism);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
