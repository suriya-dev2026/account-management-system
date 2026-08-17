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
import com.accountmanagement.constants.message.MemberMessage;
import com.accountmanagement.model.Member;
import com.accountmanagement.request.MemberRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.MemberService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "v1/member")
@Tag(name = "MemberController")
public class MemberController {

    private final MemberService memberService;

    MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addMember(@Valid @RequestBody MemberRequest memberRequest) {
        memberRequest.sanitizeInput();
        memberService.addMember(memberRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MemberMessage.ADD_MEMBER, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateMemberById(@PathVariable UUID id,
            @Valid @RequestBody MemberRequest memberRequest) {
        memberRequest.sanitizeInput();
        memberService.updateMember(id, memberRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MemberMessage.UPDATE_MEMBER, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMemberById(@PathVariable UUID id) {
        memberService.deleteMemberById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MemberMessage.DELETE_MEMBER, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> viewAll() {
        List<Member> members = memberService.viewAll();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MemberMessage.MEMBER, 200);
        response.setData(members);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
