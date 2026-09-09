package com.accountmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.VbsClassMapper;
import com.accountmanagement.model.VbsClass;
import com.accountmanagement.repository.VbsClassRepository;
import com.accountmanagement.request.VbsClassRequest;

@Service
public class VbsClassService {

    private final VbsClassRepository vbsClassRepository;

    private final VbsClassMapper vbsClassMapper;

    public VbsClassService(VbsClassRepository vbsClassRepository, VbsClassMapper vbsClassMapper) {
        this.vbsClassRepository = vbsClassRepository;
        this.vbsClassMapper = vbsClassMapper;
    }

    public VbsClass createVbsClass(VbsClassRequest vbsClassRequest) {
        validateClassName(vbsClassRequest);
        validateClassNameAndTeacherId(vbsClassRequest);
        VbsClass vbsClass = vbsClassMapper.toAddVbsClass(vbsClassRequest);
        return vbsClassRepository.save(vbsClass);
    }

    public VbsClass updateVbsClass(Integer id, VbsClassRequest vbsClassRequest) {
        VbsClass vbsClass = findVbsClassById(id);
        VbsClass updatedVbsClass = vbsClassMapper.toUpdateVbsClass(vbsClass, vbsClassRequest);
        return vbsClassRepository.save(updatedVbsClass);
    }

    public void deleteVbsClassById(Integer id) {
        VbsClass vbsClass = findVbsClassById(id);
        vbsClass.setStatus(AppConstants.INACTIVE);
        vbsClassRepository.save(vbsClass);
    }

    public List<VbsClass> viewAllVbsClass() {
        return vbsClassRepository.findAll();
    }

    public VbsClass findVbsClassById(Integer id) {
        return vbsClassRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Vbs Class Id Not Found"));
    }

    private void validateClassName(VbsClassRequest vbsClassRequest) {
        boolean exists = vbsClassRepository.existsByClassName(vbsClassRequest.getClassName());
        if (exists) {
            throw new DuplicateRecordException("Class Name Already Exists");
        }
    }

    private void validateClassNameAndTeacherId(VbsClassRequest vbsClassRequest) {
        boolean exists = vbsClassRepository.existsByClassNameAndTeacherId(vbsClassRequest.getClassName(),
                vbsClassRequest.getTeacherId());
        if (exists) {
            throw new DuplicateRecordException("A VBS class with the same class name and teacher already exists.");
        }
    }

}
