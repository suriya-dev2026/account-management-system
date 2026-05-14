package com.accountmanagement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.accountmanagement.dto.UserDto;
import com.accountmanagement.model.User;
import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.request.LoginRequest;
import com.accountmanagement.request.UserRequest;
import com.accountmanagement.utility.TokenUtility;

@Service
public class UserService {

    @Autowired
    private OtpService otpService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenUtility tokenUtility;

    @Autowired
    private UserRepository userRepository;

    public User registerUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        user.setPhone(userRequest.getPhone());
        return userRepository.save(user);
    }

    public String loginUser(LoginRequest loginRequest) {
        User user = userRepository
                .findByUserNameOrEmail(loginRequest.getUserNameOrEmail(), loginRequest.getUserNameOrEmail())
                .orElseThrow(() -> new RuntimeException("Invalid username or email"));

        boolean isPasswordValid = bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        if (!isPasswordValid) {
            throw new RuntimeException("Invalid Password");
        }
        otpService.sendOtp(user.getEmail());
        return "Otp send successfully";
    }

    public Map<String, String> verifyLoginOtp(String email, String otp) {
        Map<String, String> response = new HashMap<>();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Wrong email"));
        otpService.verifyOtp(email, otp);

        String accessToken = tokenUtility.generateJwt(user.getUserName());

        String refreshKey = UUID.randomUUID().toString();

        user.setAccesstoken(accessToken);
        user.setRefreshKey(refreshKey);
        response.put("accessToken", accessToken);
        response.put("refreshKey", refreshKey);
        userRepository.save(user);
        return response;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> {
            UserDto userDto = new UserDto();
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setUserName(user.getUserName());
            userDto.setEmail(user.getEmail());
            return userDto;
        }).toList();
    }

}
