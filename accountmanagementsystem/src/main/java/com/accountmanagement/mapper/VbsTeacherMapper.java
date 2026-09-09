package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;

import com.accountmanagement.model.Member;
import com.accountmanagement.model.VbsTeacher;
import com.accountmanagement.request.VbsTeacherRequest;
import com.accountmanagement.request.VbsTeacherUpdateRequest;

@Component
public class VbsTeacherMapper {

    public VbsTeacher toCreateVbsTeacher(Member member, VbsTeacherRequest vbsTeacherRequest) {
        VbsTeacher vbsTeacher = new VbsTeacher();
        vbsTeacher.setOrganizationId(vbsTeacherRequest.getOrganizationId());
        vbsTeacher.setMemberId(vbsTeacherRequest.getMemberId());
        vbsTeacher.setClassId(vbsTeacherRequest.getClassId());
        vbsTeacher.setTeacherType(vbsTeacherRequest.getTeacherType());
        vbsTeacher.setAge(vbsTeacherRequest.getAge());
        vbsTeacher.setInterestArea(vbsTeacherRequest.getInterestArea());
        vbsTeacher.setPriority(vbsTeacherRequest.getPriority());
        if (member != null) {
            vbsTeacher.setTeacherName(member.getUserName());
            vbsTeacher.setGender(member.getGender());
            vbsTeacher.setDateOfBirth(member.getDateOfBirth());
            vbsTeacher.setDateOfJoin(member.getDateOfJoin());
        } else {
            vbsTeacher.setTeacherName(vbsTeacherRequest.getTeacherName());
            vbsTeacher.setGender(vbsTeacherRequest.getGender());
            vbsTeacher.setDateOfBirth(vbsTeacherRequest.getDateOfBirth());
            vbsTeacher.setDateOfJoin(vbsTeacherRequest.getDateOfJoin());
        }
        return vbsTeacher;
    }

    public VbsTeacher toUpdateVbsTeacher(VbsTeacher vbsTeacher, VbsTeacherUpdateRequest vbsTeacherUpdateRequest) {
        vbsTeacher.setInterestArea(vbsTeacherUpdateRequest.getInterestArea());
        vbsTeacher.setPriority(vbsTeacherUpdateRequest.getPriority());
        return vbsTeacher;
    }
}
