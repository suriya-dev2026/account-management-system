package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;
import com.accountmanagement.model.Certificate;
import com.accountmanagement.request.CertificateRequest;

@Component
public class CertificateMapper {

    public Certificate toCreateCertificate(String certificateNumber, CertificateRequest certificateRequest) {
        Certificate certificate = new Certificate();
        certificate.setOrganizationId(certificateRequest.getOrganizationId());
        certificate.setCertificateType(certificateRequest.getCertificateType());
        certificate.setMemberId(certificateRequest.getMemberId());
        certificate.setMemberName(certificateRequest.getMemberName());
        certificate.setFatherName(certificateRequest.getFatherName());
        certificate.setMotherName(certificateRequest.getMotherName());
        certificate.setIssueDate(certificateRequest.getIssueDate());
        certificate.setEventDate(certificateRequest.getEventDate());
        certificate.setIssuedBy(certificateRequest.getIssuedBy());
        certificate.setCertificateNumber(certificateNumber);
        return certificate;
    }

    public Certificate toUpdateCertificate(String certificateNumber, Certificate certificate,
            CertificateRequest certificateRequest) {
        certificate.setOrganizationId(certificateRequest.getOrganizationId());
        certificate.setCertificateType(certificateRequest.getCertificateType());
        certificate.setMemberId(certificateRequest.getMemberId());
        certificate.setMemberName(certificateRequest.getMemberName());
        certificate.setFatherName(certificateRequest.getFatherName());
        certificate.setMotherName(certificateRequest.getMotherName());
        certificate.setIssueDate(certificateRequest.getIssueDate());
        certificate.setEventDate(certificateRequest.getEventDate());
        certificate.setIssuedBy(certificateRequest.getIssuedBy());
        certificate.setCertificateNumber(certificateNumber);
        return certificate;
    }
}
