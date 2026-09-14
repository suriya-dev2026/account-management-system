package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.Baptism;
import com.accountmanagement.repository.BaptismRepository;
import com.accountmanagement.request.BaptismRequest;

@Service
public class BaptismService {

    private final BaptismRepository baptismRepository;

    public BaptismService(BaptismRepository baptismRepository) {
        this.baptismRepository = baptismRepository;
    }

    public Baptism createBaptism(BaptismRequest baptismRequest) {
        Baptism baptism = new Baptism();
        baptism.setOrganizationId(baptismRequest.getOrganizationId());
        baptism.setMemberId(baptismRequest.getMemberId());
        baptism.setBaptismDate(baptismRequest.getBaptismDate());
        baptism.setNotes(baptismRequest.getNotes());
        return baptismRepository.save(baptism);
    }

    public Baptism updateBaptism(UUID id, BaptismRequest baptismRequest) {
        Baptism baptism = findBaptismById(id);
        baptism.setMemberId(baptismRequest.getMemberId());
        baptism.setBaptismDate(baptismRequest.getBaptismDate());
        baptism.setNotes(baptismRequest.getNotes());
        return baptismRepository.save(baptism);
    }

    public void deleteBaptismById(UUID id) {
        findBaptismById(id);
        baptismRepository.deleteById(id);
    }

    public List<Baptism> viewAllBaptism() {
        return baptismRepository.findAll();
    }

    public Baptism findBaptismById(UUID id) {
        return baptismRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Baptism Id Not Found"));
    }
}
