package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.model.SundaySchoolTransition;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class SundaySchoolTransitionListener {

    @PrePersist
    public void onCreate(SundaySchoolTransition sundaySchoolTransition) {
        sundaySchoolTransition.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(SundaySchoolTransition sundaySchoolTransition) {
        sundaySchoolTransition.setUpdatedAt(LocalDateTime.now());
    }

}
