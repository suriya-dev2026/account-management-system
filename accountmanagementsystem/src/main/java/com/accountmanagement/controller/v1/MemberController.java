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
import com.accountmanagement.constants.message.MemberCategoryMessage;
import com.accountmanagement.constants.message.MemberMessage;
import com.accountmanagement.model.Member;
import com.accountmanagement.model.MemberCategory;
import com.accountmanagement.request.MemberCategoryRequest;
import com.accountmanagement.request.MemberRequest;
import com.accountmanagement.request.MemberUpdateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.MemberCategoryService;
import com.accountmanagement.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/member")
@Tag(name = "MemberController")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final MemberCategoryService memberCategoryService;

    @PostMapping("/category/create")
    public ResponseEntity<ApiResponse> createMemberCategory(
            @Valid @RequestBody MemberCategoryRequest memberCategoryRequest) {
        memberCategoryRequest.sanitizeInput();
        memberCategoryService.createMemberCategory(memberCategoryRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MemberCategoryMessage.ADD_MEMBER_CATEGORY, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/category/update/{id}")
    public ResponseEntity<ApiResponse> updateMemberCategory(@PathVariable Integer id,
            @Valid @RequestBody MemberCategoryRequest memberCategoryRequest) {
        memberCategoryRequest.sanitizeInput();
        memberCategoryService.updateMemberCategory(id, memberCategoryRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MemberCategoryMessage.UPDATE_MEMBER_CATEGORY, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMemberCategoryById(@PathVariable Integer id) {
        memberCategoryService.deleteMemberCateogoryById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MemberCategoryMessage.DELETE_MEMBER_CATEGORY, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/category/view")
    public ResponseEntity<ApiResponse> viewAllMemberCategories() {
        List<MemberCategory> memberCategory = memberCategoryService.viewAll();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MemberCategoryMessage.MEMBER_CATEGORY, 200);
        response.setData(memberCategory);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> addMember(@Valid @RequestBody MemberRequest memberRequest) {
        memberRequest.sanitizeInput();
        memberService.addMember(memberRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MemberMessage.ADD_MEMBER, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateMemberById(@PathVariable UUID id,
            @Valid @RequestBody MemberUpdateRequest memberUpdateRequest) {
        memberUpdateRequest.sanitizeInput();
        memberService.updateMember(id, memberUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MemberMessage.UPDATE_MEMBER, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMemberById(@PathVariable UUID id) {
        memberService.deleteMemberById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MemberMessage.DELETE_MEMBER, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/view")
    public ResponseEntity<ApiResponse> viewAll() {
        List<Member> members = memberService.viewAll();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MemberMessage.MEMBER, 200);
        response.setData(members);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
