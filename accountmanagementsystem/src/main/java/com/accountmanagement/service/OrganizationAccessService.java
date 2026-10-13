package com.accountmanagement.service;

import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.accountmanagement.enums.UserType;
import com.accountmanagement.model.User;
import com.accountmanagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationAccessService {

    private final UserRepository userRepository;

    public UUID getCurrentOrganizationId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof User)) {
            throw new AccessDeniedException(
                    "User is not authenticated");
        }
        User currentUser = (User) authentication.getPrincipal();
        if (currentUser.getUserType() == UserType.OWNER) {
            throw new AccessDeniedException(
                    "Owner cannot directly create organization-specific records");
        }
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new AccessDeniedException("User not found"));
        if (user.getOrganizationId() == null) {
            throw new AccessDeniedException(
                    "User is not associated with an organization");
        }
        return user.getOrganizationId();
    }
}
