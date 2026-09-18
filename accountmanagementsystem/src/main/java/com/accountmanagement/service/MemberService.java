package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.MemberMessage;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.exceptions.UserAlreadyExistsException;
import com.accountmanagement.mapper.MemberMapper;
import com.accountmanagement.model.Member;
import com.accountmanagement.model.User;
import com.accountmanagement.repository.MemberRepository;
import com.accountmanagement.request.MemberRequest;
import com.accountmanagement.request.MemberUpdateRequest;
import com.accountmanagement.utility.Apputility;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    private final UserLoginAuditLogService userLoginAuditLogService;

    MemberService(MemberRepository memberRepository, MemberMapper memberMapper,
            UserLoginAuditLogService userLoginAuditLogService) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
        this.userLoginAuditLogService = userLoginAuditLogService;
    }

    public Member addMember(MemberRequest memberRequest) {
        validateMember(memberRequest);
        User user = getLoggedUser();
        Member member = memberMapper.toAddMember(memberRequest);
        Member savedMember = memberRepository.save(member);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Add Member", "Success");
        return savedMember;
    }

    public Member updateMember(UUID id, MemberUpdateRequest memberRequest) {
        Member member = findByMemberId(id);
        User user = getLoggedUser();
        Member updatedMember = memberMapper.toUpdateMember(member, memberRequest);
        Member newUpdatedMember = memberRepository.save(updatedMember);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Update Member", "Success");
        return newUpdatedMember;
    }

    public void deleteMemberById(UUID id) {
        Member member = findByMemberId(id);
        member.setStatus(AppConstants.INACTIVE);
        memberRepository.save(member);
        User user = Apputility.getLoggedUser();
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Delete Member", "Success");
    }

    private Member findByMemberId(UUID id) {
        return memberRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Member id not found"));
    }

    private User getLoggedUser() {
        return Apputility.getLoggedUser();
    }

    public List<Member> viewAll() {
        User user = getLoggedUser();
        List<Member> member = memberRepository.findAll();
        if (member.isEmpty() || member == null) {
            throw new RecordNotFoundException(MemberMessage.MEMBER_NOT_FOUND);
        }
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Get All Members", "Success");
        return member;
    }

    private void validateMember(MemberRequest memberRequest) {
        if (memberRepository.existsByUserName(memberRequest.getUserName())) {
            throw new UserAlreadyExistsException("user name already exists");
        }
        if (memberRepository.existsByEmail(memberRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }
        if (memberRepository.existsByContactNumber(memberRequest.getContactNumber())) {
            throw new UserAlreadyExistsException("Phone number already registered");
        }
    }

}
