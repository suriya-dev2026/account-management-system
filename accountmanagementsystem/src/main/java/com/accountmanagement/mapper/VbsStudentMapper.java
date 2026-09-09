package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;
import com.accountmanagement.model.Member;
import com.accountmanagement.model.VbsStudent;
import com.accountmanagement.request.VbsStudentRequest;
import com.accountmanagement.request.VbsStudentUpdateRequest;

@Component
public class VbsStudentMapper {

    public VbsStudent toAddVbsStudent(Member member, VbsStudentRequest request) {
        VbsStudent vbsStudent = new VbsStudent();
        vbsStudent.setOrganizationId(request.getOrganizationId());
        vbsStudent.setVbsYearId(request.getVbsYearId());
        vbsStudent.setVbsClassId(request.getVbsClassId());
        vbsStudent.setMemberId(request.getMemberId());
        vbsStudent.setEmergencyContactNumber(request.getEmergencyContactNumber());
        vbsStudent.setEmergencyContactPerson(request.getEmergencyContactPerson());
        if (member != null) {
            vbsStudent.setStudentName(member.getUserName());
            vbsStudent.setGender(member.getGender());
            vbsStudent.setContactNumber(member.getContactNumber());
            vbsStudent.setAddress(member.getAddress());
        } else {
            vbsStudent.setStudentName(request.getStudentName());
            vbsStudent.setGender(request.getGender());
            vbsStudent.setContactNumber(request.getContactNumber());
            vbsStudent.setAddress(request.getAddress());
        }
        return vbsStudent;
    }

    public VbsStudent toUpdateVbsStudent(VbsStudent vbsStudent, VbsStudentUpdateRequest vbsStudentUpdateRequest) {
        vbsStudent.setEmergencyContactNumber(vbsStudentUpdateRequest.getEmergencyContactNumber());
        vbsStudent.setEmergencyContactPerson(vbsStudentUpdateRequest.getEmergencyContactPerson());
        vbsStudent.setVbsClassId(vbsStudentUpdateRequest.getVbsClassId());
        return vbsStudent;
    }

}
