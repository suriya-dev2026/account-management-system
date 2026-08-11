package com.accountmanagement.mapper;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.accountmanagement.model.User;
import com.accountmanagement.model.UserProfile;
import com.accountmanagement.request.UserRegistrationRequest;
import com.accountmanagement.request.UserUpdationRequest;

@Component
public class UserMapper {

    public User toRegisterUser(UserRegistrationRequest userRegistrationRequest) {
        User user = new User();
        user.setOrganizationId(userRegistrationRequest.getOrganizationId());
        user.setUserName(userRegistrationRequest.getUserName());
        user.setEmail(userRegistrationRequest.getEmail());
        user.setContactNumber(userRegistrationRequest.getContactNumber());
        user.setUserType(userRegistrationRequest.getUserType());
        return user;
    }

    public UserProfile toRegisterUserProfile(UUID id, UserRegistrationRequest userRegistrationRequest) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(id);
        userProfile.setFirstName(userRegistrationRequest.getFirstName());
        userProfile.setLastName(userRegistrationRequest.getLastName());
        userProfile.setAddress(userRegistrationRequest.getAddress());
        userProfile.setGender(userRegistrationRequest.getGender());
        userProfile.setDateOfBirth(userRegistrationRequest.getDateOfBirth());
        return userProfile;
    }

    public User toUpdateUser(User user, UserUpdationRequest userUpdationRequest) {
        user.setUserType(userUpdationRequest.getUserType());
        return user;
    }

    public UserProfile toUpdateUserProfile(UserProfile userProfile, UserUpdationRequest userUpdationRequest) {
        userProfile.setFirstName(userUpdationRequest.getFirstName());
        userProfile.setLastName(userUpdationRequest.getLastName());
        userProfile.setAddress(userUpdationRequest.getAddress());
        userProfile.setDateOfBirth(userUpdationRequest.getDateOfBirth());
        return userProfile;
    }
}
