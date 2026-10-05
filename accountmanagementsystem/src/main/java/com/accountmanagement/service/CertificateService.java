package com.accountmanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.CertificateMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.CertificateMapper;
import com.accountmanagement.model.Certificate;
import com.accountmanagement.repository.CertificateRepository;
import com.accountmanagement.request.CertificateRequest;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;

    private final CertificateMapper certificateMapper;

    public CertificateService(CertificateRepository certificateRepository, CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }

    public Certificate createCertificate(CertificateRequest certificateRequest) {
        validateCertificate(certificateRequest);
        String certificateNumber = generateCertificateNumber(certificateRequest.getCertificateType());
        Certificate certificate = certificateMapper.toCreateCertificate(certificateNumber, certificateRequest);
        return certificateRepository.save(certificate);
    }

    public Certificate updateCertificateById(UUID id, CertificateRequest certificateRequest) {
        String certificateNumber = generateCertificateNumber(certificateRequest.getCertificateType());
        Certificate certificate = findCertificateById(id);
        Certificate updatedCertificate = certificateMapper.toUpdateCertificate(certificateNumber,certificate, certificateRequest);
        return certificateRepository.save(updatedCertificate);
    }

    public void deleteCertificateById(UUID id) {
        Certificate certificate = findCertificateById(id);
        certificate.setStatus(AppConstants.INACTIVE);
        certificateRepository.save(certificate);
    }

    public List<Certificate> viewAllCertificates() {
        return certificateRepository.findAll();
    }

    public Certificate findCertificateById(UUID id) {
        return certificateRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(CertificateMessage.CERTIFICATE_NOT_FOUND));
    }

    private String generateCertificateNumber(String certificateType) {
        long count = certificateRepository.count() + 1;
        String year = String.valueOf(LocalDate.now().getYear()).substring(2);
        String typeCode = switch (certificateType.toUpperCase()) {
            case "BAPTISM" -> "BAPT";
            case "MARRIAGE" -> "MARR";
            case "DEDICATION" -> "DEDI";
            case "MEMBER" -> "MEMB";
            case "MEMORIAL" -> "MEMOR";
            default -> "CER";
        };
        return String.format(
                "CH-%s-%s-%05d",
                year,
                typeCode,
                count);
    }

    private void validateCertificate(CertificateRequest certificateRequest) {
        boolean exists = certificateRepository.existsByMemberIdAndCertificateType(certificateRequest.getMemberId(),
                certificateRequest.getCertificateType());
        if (exists) {
            throw new DuplicateRecordException("Certificate Record Exists");
        }
    }
}
