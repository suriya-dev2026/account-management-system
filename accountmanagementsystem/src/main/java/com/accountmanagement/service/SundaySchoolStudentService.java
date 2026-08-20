package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.SundaySchoolStudentMapper;
import com.accountmanagement.model.Member;
import com.accountmanagement.model.SundaySchoolStudent;
import com.accountmanagement.repository.MemberRepository;
import com.accountmanagement.repository.SundaySchoolStudentRepository;
import com.accountmanagement.request.SundaySchoolStudentRequest;
import com.accountmanagement.request.SundaySchoolStudentUpdateRequest;

@Service
public class SundaySchoolStudentService {

    private final SundaySchoolStudentRepository sundaySchoolStudentRepository;

    private final MemberRepository memberRepository;

    private final SundaySchoolStudentMapper sundaySchoolStudentMapper;

    public SundaySchoolStudentService(SundaySchoolStudentRepository sundaySchoolStudentRepository,
            MemberRepository memberRepository, SundaySchoolStudentMapper sundaySchoolStudentMapper) {
        this.sundaySchoolStudentRepository = sundaySchoolStudentRepository;
        this.memberRepository = memberRepository;
        this.sundaySchoolStudentMapper = sundaySchoolStudentMapper;
    }

    public SundaySchoolStudent addSundaySchoolStudent(SundaySchoolStudentRequest sundaySchoolStudentRequest) {
        Member member = null;
        if (sundaySchoolStudentRequest.getMemberId() != null) {
            member = memberRepository.findById(sundaySchoolStudentRequest.getMemberId())
                    .orElseThrow(() -> new RecordNotFoundException("Member not found"));
        }
        SundaySchoolStudent student = sundaySchoolStudentMapper.toAddSundaySchoolStudent(sundaySchoolStudentRequest,
                member);
        return sundaySchoolStudentRepository.save(student);
    }

    public SundaySchoolStudent updateSundaySchoolStudent(UUID id,
            SundaySchoolStudentUpdateRequest sundaySchoolStudentUpdateRequest) {
        SundaySchoolStudent sundaySchoolStudent = findSundaySchoolStudentById(id);
        SundaySchoolStudent updatedSundaySchoolStudent = sundaySchoolStudentMapper
                .toUpdateSundaySchoolStudent(sundaySchoolStudent, sundaySchoolStudentUpdateRequest);
        return sundaySchoolStudentRepository.save(updatedSundaySchoolStudent);
    }

    public void deleteSundaySchoolStudentById(UUID id) {
        SundaySchoolStudent sundaySchoolStudent = findSundaySchoolStudentById(id);
        sundaySchoolStudent.setStatus(AppConstants.INACTIVE);
        sundaySchoolStudentRepository.save(sundaySchoolStudent);
    }

    public SundaySchoolStudent findSundaySchoolStudentById(UUID id) {
        return sundaySchoolStudentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Sunday School Student Id Not Found"));
    }

    public List<SundaySchoolStudent> viewAllSundaySchoolStudent() {
        return sundaySchoolStudentRepository.findAll();
    }
}
