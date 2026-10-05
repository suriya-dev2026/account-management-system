package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.Certificate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class CertificateListener {

    @PrePersist
    public void onCreateCertificate(Certificate certificate) {
        certificate.setStatus(AppConstants.ACTIVE);
        certificate.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateCertificate(Certificate certificate) {
        certificate.setUpdatedAt(LocalDateTime.now());
    }

}
