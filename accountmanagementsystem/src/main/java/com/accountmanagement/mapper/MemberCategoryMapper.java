package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;
import com.accountmanagement.model.MemberCategory;
import com.accountmanagement.request.MemberCategoryRequest;

@Component
public class MemberCategoryMapper {

    public MemberCategory toCreateMemberCategory(MemberCategoryRequest memberCategoryRequest) {
        MemberCategory memberCategory = new MemberCategory();
        memberCategory.setCategory(memberCategoryRequest.getCategory());
        memberCategory.setDescription(memberCategoryRequest.getDescription());
        return memberCategory;
    }

    public MemberCategory toUpdateMemberCategory(MemberCategory memberCategory,
            MemberCategoryRequest memberCategoryRequest) {
        memberCategory.setCategory(memberCategoryRequest.getCategory());
        memberCategory.setDescription(memberCategoryRequest.getDescription());
        return memberCategory;
    }

}
