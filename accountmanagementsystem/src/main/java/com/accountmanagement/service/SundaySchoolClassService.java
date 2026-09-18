package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.SundaySchoolClassMessage;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.exceptions.UserAlreadyExistsException;
import com.accountmanagement.model.SundaySchoolClass;
import com.accountmanagement.repository.SundaySchoolClassRepository;
import com.accountmanagement.request.SundaySchoolClassRequest;
import com.accountmanagement.request.SundaySchoolClassUpdateRequest;

@Service
public class SundaySchoolClassService {

    private final SundaySchoolClassRepository sundaySchoolClassRepository;

    public SundaySchoolClassService(SundaySchoolClassRepository sundaySchoolClassRepository) {
        this.sundaySchoolClassRepository = sundaySchoolClassRepository;
    }

    public SundaySchoolClass addSundaySchoolClass(SundaySchoolClassRequest sundaySchoolClassRequest) {
        validateSundaySchoolClass(sundaySchoolClassRequest);
        SundaySchoolClass sundaySchoolClass = new SundaySchoolClass();
        sundaySchoolClass.setOrganizationId(sundaySchoolClassRequest.getOrganizationId());
        sundaySchoolClass.setClassName(sundaySchoolClassRequest.getClassName());
        sundaySchoolClass.setClassNumber(sundaySchoolClassRequest.getClassNumber());
        return sundaySchoolClassRepository.save(sundaySchoolClass);
    }

    public SundaySchoolClass updateSundaySchoolClass(UUID id, SundaySchoolClassUpdateRequest sundaySchoolClassRequest) {
        SundaySchoolClass sundaySchoolClass = findBySundaySchoolClassById(id);
        sundaySchoolClass.setClassName(sundaySchoolClassRequest.getClassName());
        sundaySchoolClass.setClassNumber(sundaySchoolClassRequest.getClassNumber());
        return sundaySchoolClassRepository.save(sundaySchoolClass);
    }

    public void deleteSundaySchoolClassById(UUID id) {
        SundaySchoolClass sundaySchoolClass = findBySundaySchoolClassById(id);
        sundaySchoolClass.setStatus(AppConstants.INACTIVE);
        sundaySchoolClassRepository.save(sundaySchoolClass);
    }

    public SundaySchoolClass findBySundaySchoolClassById(UUID id) {
        return sundaySchoolClassRepository.findById(id)
                .orElseThrow(
                        () -> new RecordNotFoundException(SundaySchoolClassMessage.SUNDAY_SCHOOL_CLASS_ID_NOT_FOUND));
    }

    public List<SundaySchoolClass> viewAllSundaySchoolClasses() {
        return sundaySchoolClassRepository.findAll();
    }

    private void validateClassName(String className) {
        if (sundaySchoolClassRepository.existsByClassName(className)) {
            throw new UserAlreadyExistsException(SundaySchoolClassMessage.CLASS_NAME_EXISTS);
        }
    }

    private void validateClassNumber(Integer classNumber) {
        if (sundaySchoolClassRepository.existsByClassNumber(classNumber)) {
            throw new UserAlreadyExistsException(SundaySchoolClassMessage.CLASS_NUMBER_EXISTS);
        }
    }

    private void validateSundaySchoolClass(SundaySchoolClassRequest sundaySchoolClassRequest) {
        validateClassName(sundaySchoolClassRequest.getClassName());
        validateClassNumber(sundaySchoolClassRequest.getClassNumber());
    }
}
