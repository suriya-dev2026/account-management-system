package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.Member;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class MemberListener {

    @PrePersist
    public void onCreateMember(Member member) {
        member.setStatus(AppConstants.ACTIVE);
        member.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateMember(Member member) {
        member.setUpdatedAt(LocalDateTime.now());
    }
}
