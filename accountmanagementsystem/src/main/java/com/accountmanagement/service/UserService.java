 package com.accountmanagement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.accountmanagement.dto.UserDto;
import com.accountmanagement.exceptions.RecordNotFoundException;
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
        if (userRepository.existsByUserName(userRequest.getUserName())) {
            throw new RecordNotFoundException("Username already exists");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RecordNotFoundException("Email already registered");
        }
        if (userRepository.existsByPhone(userRequest.getPhone())) {
            throw new RecordNotFoundException("Phone already registered");
        }
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
                .findByUserNameOrEmailOrPhone(loginRequest.getUserNameOrEmailOrPhone(),
                        loginRequest.getUserNameOrEmailOrPhone(),
                        loginRequest.getUserNameOrEmailOrPhone())
                .orElseThrow(() -> new RecordNotFoundException("invalid email or username or phone"));
        boolean isPasswordValid = bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        if (!isPasswordValid) {
            throw new RuntimeException("Invalid Password");
        }
        otpService.sendOtp(user);
        return "Otp send successfully";
    }

    public Map<String, String> verifyLoginOtp(String email, String otp) {
        Map<String, String> response = new HashMap<>();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException("Wrong email"));
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

    public String generateAccessToken(String refreshKey) {
        User user = userRepository.findByRefreshKey(refreshKey);
        if (user == null) {
            throw new RecordNotFoundException("Invalid Refresh Key");
        }
        String newToken = tokenUtility.generateJwt(user.getUserName());
        user.setAccesstoken(newToken);
        userRepository.save(user);
        return newToken;
    }

}
