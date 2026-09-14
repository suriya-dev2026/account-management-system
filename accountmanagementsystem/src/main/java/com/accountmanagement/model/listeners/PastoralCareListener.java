package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.PastoralCare;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class PastoralCareListener {

    @PrePersist
    public void onCreatePastorlCare(PastoralCare pastoralCare) {
        pastoralCare.setStatus(AppConstants.SUCCESS);
        pastoralCare.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdatePastorlCare(PastoralCare pastoralCare) {
        pastoralCare.setUpdatedAt(LocalDateTime.now());
    }

}
