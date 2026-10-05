package com.accountmanagement.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, UUID> {

    boolean existsByMemberIdAndCertificateType(UUID memberId, String certificateType);

}
