package com.accountmanagement.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.accountmanagement.model.User;
import com.accountmanagement.model.UserProfile;
import com.accountmanagement.repository.UserProfileRepository;
import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.request.UserRegistrationRequest;

@Component
public class UserMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        User newUser = userRepository.save(user);
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(newUser.getId());
        userProfile.setAddress(userRequest.getAddress());
        userProfile.setFailedLoginAttempts(0);
        userProfile.setIsAccountLocked(false);
        userProfile.setLockedTime(null);
        userProfileRepository.save(userProfile);
        return user;
    }

}
