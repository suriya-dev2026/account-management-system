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
import com.accountmanagement.constants.message.EquipmentMessage;
import com.accountmanagement.model.Equipment;
import com.accountmanagement.request.EquipmentRequest;
import com.accountmanagement.request.EquipmentUpdateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.EquipmentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/equipment")
@Tag(name = "EquipmentController")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createEquipment(
            @Valid @RequestBody EquipmentRequest equipmentRequest) {
        equipmentRequest.sanitizeInput();
        equipmentService.createEquipment(equipmentRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EquipmentMessage.CREATE_EQUIPMENT,
                201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateEquipmentById(@PathVariable UUID id,
            @Valid @RequestBody EquipmentUpdateRequest equipmentUpdateRequest) {
        equipmentUpdateRequest.sanitizeInput();
        equipmentService.updateEquipment(id, equipmentUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EquipmentMessage.UPDATE_EQUIPMENT,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteEquipmentById(@PathVariable UUID id) {
        equipmentService.deleteEquipmentById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EquipmentMessage.DELETE_EQUIPMENT,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllEquipments() {
        List<Equipment> equipment = equipmentService.viewAllEquipments();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                EquipmentMessage.VIEW_ALL_EQUIPMENTS,
                200);
        response.setData(equipment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
