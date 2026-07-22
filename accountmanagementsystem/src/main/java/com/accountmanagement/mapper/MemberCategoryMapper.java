package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;
import com.accountmanagement.model.MemberCategory;
import com.accountmanagement.request.MemberCategoryRequest;

@Component
public class MemberCategoryMapper {

    public MemberCategory addMemberCategory(MemberCategoryRequest memberCategoryRequest) {
        MemberCategory memberCategory = new MemberCategory();
        memberCategory.setCategory(memberCategoryRequest.getCategory());
        memberCategory.setDescription(memberCategoryRequest.getDescription());
        return memberCategory;
    }

    public MemberCategory updateMemberCategory(MemberCategory memberCategory,
            MemberCategoryRequest memberCategoryRequest) {
        memberCategory.setCategory(memberCategoryRequest.getCategory());
        memberCategory.setDescription(memberCategoryRequest.getDescription());
        return memberCategory;
    }

}
