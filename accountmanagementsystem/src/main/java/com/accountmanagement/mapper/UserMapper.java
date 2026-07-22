package com.accountmanagement.mapper;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.accountmanagement.model.User;
import com.accountmanagement.model.UserProfile;
import com.accountmanagement.request.UserRegistrationRequest;

@Component
public class UserMapper {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    UserMapper(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User toRegisterUser(UserRegistrationRequest userRequest) {
        User user = new User();
        user.setOrganizationId(userRequest.getOrganizationId());
        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setContactNumber(userRequest.getContactNumber());
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        user.setUserType(userRequest.getUserType());
        user.setFailedLoginAttempts(0);
        user.setIsAccountLocked(false);
        return user;
    }

    public UserProfile toUserProfile(UserRegistrationRequest userRegistrationRequest, UUID userId) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);
        userProfile.setFirstName(userRegistrationRequest.getFirstName());
        userProfile.setLastName(userRegistrationRequest.getLastName());
        userProfile.setGender(userRegistrationRequest.getGender());
        userProfile.setDateOfBirth(userRegistrationRequest.getDateOfBirth());
        userProfile.setAddress(userRegistrationRequest.getAddress());
        return userProfile;
    }

}
