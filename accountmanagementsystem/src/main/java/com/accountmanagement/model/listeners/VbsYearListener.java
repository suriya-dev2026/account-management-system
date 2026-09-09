package com.accountmanagement.model.listeners;

import com.accountmanagement.model.VbsYear;

import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
public class VbsYearListener {

    @PrePersist
    public void onCreate(VbsYear vbsYear) {
        vbsYear.setIsActive(true);
    }
}
