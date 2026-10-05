package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.Bulletin;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class BulletinListener {

    @PrePersist
    public void onCreateBulletin(Bulletin bulletin) {
        bulletin.setStatus(AppConstants.ACTIVE);
        bulletin.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateBulletin(Bulletin bulletin) {
        bulletin.setUpdatedAt(LocalDateTime.now());
    }

}
