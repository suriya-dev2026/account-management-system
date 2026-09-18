package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;
import com.accountmanagement.model.Member;
import com.accountmanagement.request.MemberRequest;
import com.accountmanagement.request.MemberUpdateRequest;

@Component
public class MemberMapper {

    public Member toAddMember(MemberRequest memberRequest) {
        Member member = new Member();
        member.setOrganizationId(memberRequest.getOrganizationId());
        member.setOrganizationCode(memberRequest.getOrganizationCode());
        member.setCategoryId(memberRequest.getCategoryId());
        member.setLocationId(memberRequest.getLocationId());
        member.setFamilyHeadId(memberRequest.getFamilyHeadId());
        member.setRelationShip(memberRequest.getRelationship());
        member.setFirstName(memberRequest.getFirstName());
        member.setLastName(memberRequest.getLastName());
        member.setUserName(memberRequest.getUserName());
        member.setGender(memberRequest.getGender());
        member.setDateOfBirth(memberRequest.getDateOfBirth());
        member.setDateOfJoin(memberRequest.getDateOfJoin());
        member.setWeddingDate(memberRequest.getWeddingDate());
        member.setEmail(memberRequest.getEmail());
        member.setContactNumber(memberRequest.getContactNumber());
        member.setAddress(memberRequest.getAddress());
        member.setIsWaterBaptised(memberRequest.getIsWaterBaptised());
        member.setIsSpiritBaptised(memberRequest.getIsSpiritBaptised());
        return member;
    }

    public Member toUpdateMember(Member member, MemberUpdateRequest memberRequest) {
        member.setCategoryId(memberRequest.getCategoryId());
        member.setLocationId(memberRequest.getLocationId());
        member.setFamilyHeadId(memberRequest.getFamilyHeadId());
        member.setRelationShip(memberRequest.getRelationship());
        member.setFirstName(memberRequest.getFirstName());
        member.setLastName(memberRequest.getLastName());
        member.setUserName(memberRequest.getUserName());
        member.setGender(memberRequest.getGender());
        member.setDateOfBirth(memberRequest.getDateOfBirth());
        member.setDateOfJoin(memberRequest.getDateOfJoin());
        member.setWeddingDate(memberRequest.getWeddingDate());
        member.setEmail(memberRequest.getEmail());
        member.setContactNumber(memberRequest.getContactNumber());
        member.setAddress(memberRequest.getAddress());
        member.setIsWaterBaptised(memberRequest.getIsWaterBaptised());
        member.setIsSpiritBaptised(memberRequest.getIsSpiritBaptised());
        return member;
    }
}
