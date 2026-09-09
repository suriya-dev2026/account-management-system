package com.accountmanagement.service;

import com.accountmanagement.mapper.VbsStudentMapper;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.Member;
import com.accountmanagement.model.VbsStudent;
import com.accountmanagement.repository.MemberRepository;
import com.accountmanagement.repository.VbsStudentRepository;
import com.accountmanagement.request.VbsStudentRequest;
import com.accountmanagement.request.VbsStudentUpdateRequest;

@Service
public class VbsStudentService {

    private final VbsStudentMapper vbsStudentMapper;

    private final VbsStudentRepository vbsStudentRepository;

    private final MemberRepository memberRepository;

    public VbsStudentService(VbsStudentRepository vbsStudentRepository, MemberRepository memberRepository,
            VbsStudentMapper vbsStudentMapper) {
        this.vbsStudentRepository = vbsStudentRepository;
        this.memberRepository = memberRepository;
        this.vbsStudentMapper = vbsStudentMapper;
    }

    public VbsStudent createVbsStudent(VbsStudentRequest vbsStudentRequest) {
        Member member = null;
        if (vbsStudentRequest.getMemberId() != null) {
            member = memberRepository.findById(vbsStudentRequest.getMemberId())
                    .orElseThrow(() -> new RecordNotFoundException("Member Id Not Found"));
            validateContactNumber(vbsStudentRequest);
        }
        VbsStudent vbsStudent = vbsStudentMapper.toAddVbsStudent(member, vbsStudentRequest);
        return vbsStudentRepository.save(vbsStudent);
    }

    public VbsStudent updateVbsStudent(UUID id, VbsStudentUpdateRequest vbsStudentUpdateRequest) {
        VbsStudent vbsStudent = findByVbsStudentById(id);
        VbsStudent updatedVbsStudent = vbsStudentMapper.toUpdateVbsStudent(vbsStudent, vbsStudentUpdateRequest);
        return vbsStudentRepository.save(updatedVbsStudent);
    }

    public void deleteVbsStudentById(UUID id) {
        VbsStudent vbsStudent = findByVbsStudentById(id);
        vbsStudent.setStatus(AppConstants.INACTIVE);
        vbsStudentRepository.save(vbsStudent);
    }

    public List<VbsStudent> viewAllStudents() {
        return vbsStudentRepository.findAll();
    }

    public VbsStudent findByVbsStudentById(UUID id) {
        return vbsStudentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Vbs Student Id Not Found"));
    }

    public void validateContactNumber(VbsStudentRequest vbsStudentRequest) {
        String contactNumber = vbsStudentRequest.getContactNumber();
        if (contactNumber == null || contactNumber.isBlank()) {
            return;
        }
        contactNumber = contactNumber.trim();
        boolean exists = vbsStudentRepository.existsByContactNumber(vbsStudentRequest.getContactNumber());
        if (exists) {
            throw new DuplicateRecordException("Contact Number Already Exists");
        }
    }

}
