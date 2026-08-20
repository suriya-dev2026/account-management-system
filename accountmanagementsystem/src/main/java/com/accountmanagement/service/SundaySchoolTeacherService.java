package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.exceptions.UserAlreadyExistsException;
import com.accountmanagement.model.SundaySchoolTeacher;
import com.accountmanagement.repository.SundaySchoolTeacherRepository;
import com.accountmanagement.request.SundaySchoolTeacherRequest;
import com.accountmanagement.request.SundaySchoolTeacherUpdateRequest;

@Service
public class SundaySchoolTeacherService {

    private final SundaySchoolTeacherRepository sundaySchoolTeacherRepository;

    public SundaySchoolTeacherService(SundaySchoolTeacherRepository sundaySchoolTeacherRepository) {
        this.sundaySchoolTeacherRepository = sundaySchoolTeacherRepository;
    }

    public SundaySchoolTeacher addSundaySchoolTeacher(SundaySchoolTeacherRequest sundaySchoolTeacherRequest) {
        validateMemberAndClassId(sundaySchoolTeacherRequest.getMemberId(), sundaySchoolTeacherRequest.getClassId());
        SundaySchoolTeacher sundaySchoolTeacher = new SundaySchoolTeacher();
        sundaySchoolTeacher.setOrganizationId(sundaySchoolTeacherRequest.getOrganizationId());
        sundaySchoolTeacher.setMemberId(sundaySchoolTeacherRequest.getMemberId());
        sundaySchoolTeacher.setClassId(sundaySchoolTeacherRequest.getClassId());
        sundaySchoolTeacher.setDateOfJoin(sundaySchoolTeacherRequest.getDateOfJoin());
        return sundaySchoolTeacherRepository.save(sundaySchoolTeacher);
    }

    public SundaySchoolTeacher updateSundaySchoolTeacher(UUID id,
            SundaySchoolTeacherUpdateRequest sundaySchoolTeacherUpdateRequest) {
        SundaySchoolTeacher sundaySchoolTeacher = findBySundaySchoolTeacherById(id);
        sundaySchoolTeacher.setClassId(sundaySchoolTeacherUpdateRequest.getClassId());
        sundaySchoolTeacher.setDateOfJoin(sundaySchoolTeacherUpdateRequest.getDateOfJoin());
        return sundaySchoolTeacherRepository.save(sundaySchoolTeacher);
    }

    public void deleteSundaySchoolTeacherById(UUID id) {
        SundaySchoolTeacher sundaySchoolTeacher = findBySundaySchoolTeacherById(id);
        sundaySchoolTeacher.setStatus(AppConstants.INACTIVE);
        sundaySchoolTeacherRepository.save(sundaySchoolTeacher);
    }

    public SundaySchoolTeacher findBySundaySchoolTeacherById(UUID id) {
        return sundaySchoolTeacherRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Sunday School Teacher Id Not Found"));
    }

    public List<SundaySchoolTeacher> viewAllSundaySchoolTeachers() {
        return sundaySchoolTeacherRepository.findAll();
    }

    private void validateMemberAndClassId(UUID memberId, UUID classId) {
        if (sundaySchoolTeacherRepository.existsByMemberIdAndClassId(memberId, classId)) {
            throw new UserAlreadyExistsException("Member Is Already Assigned To This Class.");
        }
    }
}
