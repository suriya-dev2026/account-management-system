package com.accountmanagement.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.VbsYear;
import com.accountmanagement.repository.VbsYearRepository;
import com.accountmanagement.request.VbsYearRequest;

@Service
public class VbsYearService {

    private final VbsYearRepository vbsYearRepository;

    public VbsYearService(VbsYearRepository vbsYearRepository) {
        this.vbsYearRepository = vbsYearRepository;
    }

    public VbsYear createVbsYear(VbsYearRequest vbsYearRequest) {
        validateVbsYear(vbsYearRequest);
        VbsYear vbsYear = new VbsYear();
        vbsYear.setOrganizationId(vbsYearRequest.getOrganizationId());
        vbsYear.setYear(vbsYearRequest.getYear());
        vbsYear.setStartDate(vbsYearRequest.getStartDate());
        vbsYear.setEndDate(vbsYearRequest.getEndDate());
        return vbsYearRepository.save(vbsYear);
    }

    public VbsYear updateVbsYear(Integer id, VbsYearRequest vbsYearRequest) {
        VbsYear vbsYear = findVbsYearById(id);
        vbsYear.setOrganizationId(vbsYearRequest.getOrganizationId());
        vbsYear.setYear(vbsYearRequest.getYear());
        vbsYear.setStartDate(vbsYearRequest.getStartDate());
        vbsYear.setEndDate(vbsYearRequest.getEndDate());
        return vbsYearRepository.save(vbsYear);
    }

    public void deleteVbsYearById(Integer id) {
        findVbsYearById(id);
        vbsYearRepository.deleteById(id);
    }

    public List<VbsYear> viewAllVbsYears() {
        return vbsYearRepository.findAll();
    }

    public VbsYear findVbsYearById(Integer id) {
        return vbsYearRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Vbs Year Id Not Found"));
    }

    public void validateVbsYear(VbsYearRequest vbsYearRequest) {
        boolean exists = vbsYearRepository.existsByYearAndStartDateAndEndDate(vbsYearRequest.getYear(),
                vbsYearRequest.getStartDate(), vbsYearRequest.getEndDate());
        if (exists) {
            throw new DuplicateRecordException("Vbs Year Record Already Exists");
        }
    }

}
