package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.VbsTeacherMapper;
import com.accountmanagement.model.Member;
import com.accountmanagement.model.VbsTeacher;
import com.accountmanagement.repository.MemberRepository;
import com.accountmanagement.repository.VbsTeacherRepository;
import com.accountmanagement.request.VbsTeacherRequest;
import com.accountmanagement.request.VbsTeacherUpdateRequest;

@Service
public class VbsTeacherService {

    private final VbsTeacherRepository vbsTeacherRepository;

    private final VbsTeacherMapper vbsTeacherMapper;

    private final MemberRepository memberRepository;

    public VbsTeacherService(VbsTeacherRepository vbsTeacherRepository, VbsTeacherMapper vbsTeacherMapper,
            MemberRepository memberRepository) {
        this.vbsTeacherRepository = vbsTeacherRepository;
        this.vbsTeacherMapper = vbsTeacherMapper;
        this.memberRepository = memberRepository;
    }

    public VbsTeacher createVbsTeacher(VbsTeacherRequest vbsTeacherRequest) {
        Member member = null;
        if (vbsTeacherRequest.getMemberId() != null) {
            member = memberRepository.findById(vbsTeacherRequest.getMemberId())
                    .orElseThrow(() -> new RecordNotFoundException("Member Id Not Found"));
        }
        VbsTeacher vbsTeacher = vbsTeacherMapper.toCreateVbsTeacher(member, vbsTeacherRequest);
        return vbsTeacherRepository.save(vbsTeacher);
    }

    public VbsTeacher updateVbsTeacher(UUID id, VbsTeacherUpdateRequest vbsTeacherUpdateRequest) {
        VbsTeacher vbsTeacher = findVbsTeacherById(id);
        VbsTeacher updatedVbsTeacher = vbsTeacherMapper.toUpdateVbsTeacher(vbsTeacher, vbsTeacherUpdateRequest);
        return vbsTeacherRepository.save(updatedVbsTeacher);
    }

    public void deleteVbsTeacherById(UUID id) {
        VbsTeacher vbsTeacher = findVbsTeacherById(id);
        vbsTeacher.setStatus(AppConstants.INACTIVE);
        vbsTeacherRepository.save(vbsTeacher);
    }

    public List<VbsTeacher> viewAllVbsTeachers() {
        return vbsTeacherRepository.findAll();
    }

    public VbsTeacher findVbsTeacherById(UUID id) {
        return vbsTeacherRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Vbs Teacher Id Not Found"));
    }
}
