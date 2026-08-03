package com.accountmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.message.MemberCategoryMessage;
import com.accountmanagement.constants.message.MemberMessage;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.MemberCategoryMapper;
import com.accountmanagement.model.MemberCategory;
import com.accountmanagement.model.User;
import com.accountmanagement.repository.MemberCategoryRepository;
import com.accountmanagement.request.MemberCategoryRequest;
import com.accountmanagement.utility.Apputility;

@Service
public class MemberCategoryService {

    private final UserLoginAuditLogService userLoginAuditLogService;

    private final MemberCategoryRepository memberCategoryRepository;

    private final MemberCategoryMapper memberCategoryMapper;

    MemberCategoryService(MemberCategoryRepository memberCategoryRepository,
            MemberCategoryMapper memberCategoryMapper, UserLoginAuditLogService userLoginAuditLogService) {
        this.memberCategoryRepository = memberCategoryRepository;
        this.memberCategoryMapper = memberCategoryMapper;
        this.userLoginAuditLogService = userLoginAuditLogService;

    }

    public MemberCategory addMemberCategory(MemberCategoryRequest memberCategoryRequest) {
        MemberCategory memberCategory = memberCategoryMapper.addMemberCategory(memberCategoryRequest);
        User user = getLoggedUser();
        MemberCategory savedMemberCategory = memberCategoryRepository.save(memberCategory);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Add Member Category",
                "Success");
        return savedMemberCategory;
    }

    public MemberCategory updateMemberCategory(Integer id, MemberCategoryRequest memberCategoryRequest) {
        MemberCategory memberCategory = findById(id);
        MemberCategory newMemberCategory = memberCategoryMapper.updateMemberCategory(memberCategory,
                memberCategoryRequest);
        User user = getLoggedUser();
        MemberCategory updatedMemberCategory = memberCategoryRepository.save(newMemberCategory);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Update Member Category",
                "Success");
        return updatedMemberCategory;
    }

    public void deleteMemberCateogoryById(Integer id) {
        findById(id);
        memberCategoryRepository.deleteById(id);
        User user = getLoggedUser();
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Delete Member Category",
                "Success");
    }

    private MemberCategory findById(Integer id) {
        return memberCategoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(MemberMessage.MEMBER_NOT_FOUND));
    }

    private User getLoggedUser() {
        return Apputility.getLoggedUser();
    }

    public List<MemberCategory> viewAll() {
        User user = getLoggedUser();
        List<MemberCategory> memberCategory = memberCategoryRepository.findAll();
        if (memberCategory == null || memberCategory.isEmpty()) {
            throw new RecordNotFoundException(MemberCategoryMessage.MEMBER_CATEGORY_NOT_FOUND);
        }
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Get All Member Category",
                "Success");
        return memberCategory;
    }
}
