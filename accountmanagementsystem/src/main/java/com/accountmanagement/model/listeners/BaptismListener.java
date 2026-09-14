package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.model.Baptism;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class BaptismListener {

    @PrePersist
    public void onCreateBaptism(Baptism baptism) {
        baptism.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateBaptism(Baptism baptism) {
        baptism.setUpdatedAt(LocalDateTime.now());
    }

}
