package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.message.SundaySchoolTransitionMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.SundaySchoolTransition;
import com.accountmanagement.repository.SundaySchoolTransitionRepository;
import com.accountmanagement.request.SundaySchoolTransitionRequest;

@Service
public class SundaySchoolTransitionService {

    private final SundaySchoolTransitionRepository sundaySchoolTransitionRepository;

    public SundaySchoolTransitionService(SundaySchoolTransitionRepository sundaySchoolTransitionRepository) {
        this.sundaySchoolTransitionRepository = sundaySchoolTransitionRepository;
    }

    public SundaySchoolTransition addSundaySchoolTransition(
            SundaySchoolTransitionRequest sundaySchoolTransitionRequest) {
        validateStudentAndClass(sundaySchoolTransitionRequest);
        SundaySchoolTransition sundaySchoolTransition = new SundaySchoolTransition();
        sundaySchoolTransition.setStudentId(sundaySchoolTransitionRequest.getStudentId());
        sundaySchoolTransition.setFromClassId(sundaySchoolTransitionRequest.getFromClassId());
        sundaySchoolTransition.setToClassId(sundaySchoolTransitionRequest.getToClassId());
        sundaySchoolTransition.setTransitionBy(sundaySchoolTransitionRequest.getTransitionBy());
        sundaySchoolTransition.setTransitionDate(sundaySchoolTransitionRequest.getTransitionDate());
        return sundaySchoolTransitionRepository.save(sundaySchoolTransition);
    }

    public SundaySchoolTransition updateSundaySchoolTransition(UUID id,
            SundaySchoolTransitionRequest sundaySchoolTransitionRequest) {
        SundaySchoolTransition sundaySchoolTransition = findSundaySchoolTransitionById(id);
        sundaySchoolTransition.setStudentId(sundaySchoolTransitionRequest.getStudentId());
        sundaySchoolTransition.setFromClassId(sundaySchoolTransitionRequest.getFromClassId());
        sundaySchoolTransition.setToClassId(sundaySchoolTransitionRequest.getToClassId());
        sundaySchoolTransition.setTransitionBy(sundaySchoolTransitionRequest.getTransitionBy());
        sundaySchoolTransition.setTransitionDate(sundaySchoolTransitionRequest.getTransitionDate());
        return sundaySchoolTransitionRepository.save(sundaySchoolTransition);
    }

    public void deleteSundaySchoolTransitionById(UUID id) {
        findSundaySchoolTransitionById(id);
        sundaySchoolTransitionRepository.deleteById(id);
    }

    public List<SundaySchoolTransition> viewAllSundaySchoolTransition() {
        return sundaySchoolTransitionRepository.findAll();
    }

    public SundaySchoolTransition findSundaySchoolTransitionById(UUID id) {
        return sundaySchoolTransitionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        SundaySchoolTransitionMessage.SUNDAY_SCHOOL_TRANSITION_ID_NOT_FOUND));
    }

    public void validateStudentAndClass(SundaySchoolTransitionRequest schoolTransitionRequest) {
        Boolean exists = sundaySchoolTransitionRepository.existsByStudentIdAndFromClassIdAndToClassId(
                schoolTransitionRequest.getStudentId(), schoolTransitionRequest.getFromClassId(),
                schoolTransitionRequest.getToClassId());
        if (exists) {
            throw new DuplicateRecordException(SundaySchoolTransitionMessage.STUDENT_EXISTS);
        }
    }
}
