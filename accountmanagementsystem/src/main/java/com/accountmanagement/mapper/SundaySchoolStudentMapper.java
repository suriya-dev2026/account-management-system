package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;

import com.accountmanagement.model.Member;
import com.accountmanagement.model.SundaySchoolStudent;
import com.accountmanagement.request.SundaySchoolStudentRequest;
import com.accountmanagement.request.SundaySchoolStudentUpdateRequest;

@Component
public class SundaySchoolStudentMapper {

    public SundaySchoolStudent toAddSundaySchoolStudent(SundaySchoolStudentRequest sundaySchoolStudentRequest,
            Member member) {
        SundaySchoolStudent sundaySchoolStudent = new SundaySchoolStudent();
        sundaySchoolStudent.setOrganizationId(sundaySchoolStudentRequest.getOrganizationId());
        sundaySchoolStudent.setMemberId(sundaySchoolStudentRequest.getMemberId());
        sundaySchoolStudent.setName(sundaySchoolStudentRequest.getName());
        sundaySchoolStudent.setGender(sundaySchoolStudentRequest.getGender());
        sundaySchoolStudent.setAge(sundaySchoolStudentRequest.getAge());
        sundaySchoolStudent.setDateOfBirth(sundaySchoolStudentRequest.getDateOfBirth());
        sundaySchoolStudent.setClassId(sundaySchoolStudentRequest.getClassId());
        sundaySchoolStudent.setTeacherId(sundaySchoolStudentRequest.getTeacherId());
        if (member != null) {
            sundaySchoolStudent.setName(member.getUserName());
            sundaySchoolStudent.setDateOfBirth(member.getDateOfBirth());
        } else {
            sundaySchoolStudent.setName(sundaySchoolStudentRequest.getName());
            sundaySchoolStudent.setDateOfBirth(sundaySchoolStudentRequest.getDateOfBirth());
        }
        return sundaySchoolStudent;
    }

    public SundaySchoolStudent toUpdateSundaySchoolStudent(SundaySchoolStudent sundaySchoolStudent,
            SundaySchoolStudentUpdateRequest sundaySchoolStudentRequest) {
        sundaySchoolStudent.setName(sundaySchoolStudentRequest.getName());
        sundaySchoolStudent.setAge(sundaySchoolStudentRequest.getAge());
        sundaySchoolStudent.setDateOfBirth(sundaySchoolStudentRequest.getDateOfBirth());
        sundaySchoolStudent.setClassId(sundaySchoolStudentRequest.getClassId());
        return sundaySchoolStudent;
    }
}
