package com.accountmanagement.mapper;

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

    public User toEntity(UserRegistrationRequest userRequest) {
        if (userRequest == null) {
            return null;
        }
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        user.setPhone(userRequest.getPhone());
        user.setRole((userRequest.getRole()));
        return user;
    }

    public UserProfile toUserProfile(UserRegistrationRequest userRegistrationRequest, String userId) {
        if (userRegistrationRequest == null) {
            return null;
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);
        userProfile.setAddress(userRegistrationRequest.getAddress());
        userProfile.setFailedLoginAttempts(0);
        userProfile.setIsAccountLocked(false);
        userProfile.setLockedTime(null);
        return userProfile;
    }

}
