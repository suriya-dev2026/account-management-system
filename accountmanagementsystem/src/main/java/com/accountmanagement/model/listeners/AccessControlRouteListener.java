package com.accountmanagement.model.listeners;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.AccessControlRoute;

import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
public class AccessControlRouteListener {

    @PrePersist
    public void onCreate(AccessControlRoute accessControlRoute) {
        accessControlRoute.setStatus(AppConstants.ACTIVE);
    }
}
