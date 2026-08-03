package com.accountmanagement.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.accountmanagement.model.Pincode;
import com.accountmanagement.repository.PincodeRepository;

@Service
public class PincodeService {

    private final PincodeRepository pincodeRepository;

    public PincodeService(PincodeRepository pincodeRepository) {
        this.pincodeRepository = pincodeRepository;
    }

    public List<Pincode> getAllPincodes() {
        return pincodeRepository.findAll();
    }

}
