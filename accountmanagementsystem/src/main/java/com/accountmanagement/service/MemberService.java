package com.accountmanagement.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.MemberMapper;
import com.accountmanagement.model.Member;
import com.accountmanagement.model.User;
import com.accountmanagement.repository.MemberRepository;
import com.accountmanagement.request.MemberRequest;
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
        User user = getLoggedUser();
        Member member = memberMapper.toAddMember(memberRequest);
        Member savedMember = memberRepository.save(member);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Add Member", "Success");
        return savedMember;
    }

    public Member updateMember(UUID id, MemberRequest memberRequest) {
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

}
