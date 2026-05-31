package com.accountmanagement.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.accountmanagement.dto.UserDto;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.UserMapper;
import com.accountmanagement.model.User;
import com.accountmanagement.model.UserSession;
import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.repository.UserSessionRepository;
import com.accountmanagement.request.LoginRequest;
import com.accountmanagement.request.UserRequest;
import com.accountmanagement.utility.Apputility;
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

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private UserMapper userMapper;

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
        User user = userMapper.toEntity(userRequest);
        User registeredUser = userRepository.save(user);
        userLogService.createUserLog(registeredUser.getId(), "register", "success");
        return registeredUser;
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
        String otp = otpService.generateOtp();
        otpService.sendOtp(user.getEmail(), otp);

        userSessionService.createUserSession(user.getId(), otp);
        userLogService.createUserLog(user.getId(), "login", "success");
        return "Otp send successfully";
    }

    public Map<String, String> verifyLoginOtp(String email, String otp) {
        Map<String, String> response = new HashMap<>();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException("Wrong email"));
        otpService.verifyOtp(email, otp);
        String accessToken = tokenUtility.generateJwt(user.getUserName());
        String refreshKey = UUID.randomUUID().toString();
        response.put("accessToken", accessToken);
        response.put("refreshKey", refreshKey);
        userSessionService.updateSessionAfterOtp(user.getId(), refreshKey,accessToken);
        userLogService.createUserLog(user.getId(), "Verify Opt", "success");
        return response;
    }

    public String generateAccessToken(String refreshKey) {
        UserSession userSession = userSessionService.getRefreshKey(refreshKey);
        if (userSession == null) {
            throw new RecordNotFoundException("Refresh Key not found");
        }
        if (!userSession.getRefreshKeyStatus()) {
            throw new RuntimeException("Refresh key invalid");
        }
        if (userSession.getRefreshKeyExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh key expired.Please login again");
        }
        if (userSession.getSessionStatus().equalsIgnoreCase("logout")) {
            throw new RuntimeException("Please login again");
        }
        String newToken = tokenUtility.generateJwt(userSession.getUserId());
        return newToken;
    }

    public List<UserDto> getAllUsers() throws NullPointerException, Exception {
        User loggedUser = Apputility.getLoggedUser();
        userLogService.createUserLog(loggedUser.getId(), "Get All Users", "success");
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

    public String logoutUser() throws NullPointerException, Exception {
        User user = Apputility.getLoggedUser();
        User newUser = userRepository.findByUserName(user.getUserName());
        if (newUser == null) {
            throw new RecordNotFoundException("User not found");
        }
        UserSession userSession = userSessionRepository.findTopByUserIdOrderByCreatedAtDesc(newUser.getId());
        if (userSession == null) {
            throw new RecordNotFoundException("User not found");
        }
        userSession.setSessionStatus("logout");
        userSession.setRefreshKey(null);
        userSession.setRefreshKeyStatus(false);
        userSession.setIsValidToken(false);
        userSessionRepository.save(userSession);
        userLogService.createUserLog(user.getId(), "logout", "success");
        return "user logout successfully";
    }

}
