package com.accountmanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.MemberCategoryMessage;
import com.accountmanagement.request.MemberCategoryRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.MemberCategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "member/category")
public class MemberCategoryController {

    private final MemberCategoryService memberCategoryService;

    MemberCategoryController(MemberCategoryService memberCategoryService) {
        this.memberCategoryService = memberCategoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createMemberCategory(
            @Valid @RequestBody MemberCategoryRequest memberCategoryRequest) {
        memberCategoryRequest.sanitizeInput();
        memberCategoryService.addMemberCategory(memberCategoryRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MemberCategoryMessage.ADD_MEMBER_CATEGORY, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateMemberCategory(@PathVariable Integer id,
            @Valid @RequestBody MemberCategoryRequest memberCategoryRequest) {
        memberCategoryRequest.sanitizeInput();
        memberCategoryService.updateMemberCategory(id, memberCategoryRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MemberCategoryMessage.UPDATE_MEMBER_CATEGORY, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteMemberCategoryById(@PathVariable Integer id) {
        memberCategoryService.deleteMemberCateogoryById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MemberCategoryMessage.DELETE_MEMBER_CATEGORY, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
