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
import com.accountmanagement.constants.message.EquipmentCategoryMessage;
import com.accountmanagement.model.EquipmentCategory;
import com.accountmanagement.request.EquipmentCategoryRequest;
import com.accountmanagement.request.EquipmentCategoryUpdateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.EquipmentCategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/equipment/category")
@Tag(name = "EquipmentCategoryController")
public class EquipmentCategoryController {

    private final EquipmentCategoryService equipmentCategoryService;

    public EquipmentCategoryController(EquipmentCategoryService equipmentCategoryService) {
        this.equipmentCategoryService = equipmentCategoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createEquipmentCategory(
            @Valid @RequestBody EquipmentCategoryRequest equipmentCategoryRequest) {
        equipmentCategoryRequest.sanitizeInput();
        equipmentCategoryService.createEquipmentCategory(equipmentCategoryRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EquipmentCategoryMessage.CREATE_EQUIPMENT_CATEGORY,
                201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateEquipmentCategoryById(@PathVariable Integer id,
            @Valid @RequestBody EquipmentCategoryUpdateRequest equipmentCategoryUpdateRequest) {
        equipmentCategoryUpdateRequest.sanitizeInput();
        equipmentCategoryService.updateEquipmentCategory(id, equipmentCategoryUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EquipmentCategoryMessage.UPDATE_EQUIPMENT_CATEGORY,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteEquipmentCategoryById(@PathVariable Integer id) {
        equipmentCategoryService.deleteEquipmentCategoryById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EquipmentCategoryMessage.DELETE_EQUIPMENT_CATEGORY,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllEquipmentCategories() {
        List<EquipmentCategory> equipmentCategory = equipmentCategoryService.viewAllEquipmentCategory();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                EquipmentCategoryMessage.VIEW_ALL_EQUIPMENT_CATEGORIES,
                200);
        response.setData(equipmentCategory);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
