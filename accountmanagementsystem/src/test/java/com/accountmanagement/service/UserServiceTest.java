package com.accountmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.accountmanagement.dto.UserDto;
import com.accountmanagement.exceptions.InvalidCredentialsException;
import com.accountmanagement.exceptions.UserAlreadyExistsException;
import com.accountmanagement.mapper.UserMapper;
import com.accountmanagement.model.User;
import com.accountmanagement.model.UserProfile;
import com.accountmanagement.model.UserSession;
import com.accountmanagement.repository.UserProfileRepository;
import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.repository.UserSessionRepository;
import com.accountmanagement.request.LoginRequest;
import com.accountmanagement.request.UserRegistrationRequest;
import com.accountmanagement.request.VerifyOtpRequest;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.utility.TokenUtility;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserLogService userLogService;

    @Mock
    private OtpService otpService;

    @Mock
    private UserSessionService userSessionService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private TokenUtility tokenUtility;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @Mock
    private Apputility apputility;

    @Mock
    private UserSessionRepository userSessionRepository;

    @Mock
    private EmailQueueService emailQueueService;

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserService userService;

    UserRegistrationRequest userRequest = new UserRegistrationRequest();;

    LoginRequest loginRequest = new LoginRequest();

    @Test
    void shouldRegisterUser() {
        userRequest.setFirstName("Ajay");
        userRequest.setLastName("Kumar");
        userRequest.setUserName(("ajay123"));
        userRequest.setEmail("ajay@gmail.com");
        userRequest.setPassword("Ajay@123");
        userRequest.setConfirmPassword("Ajay@123");
        userRequest.setPhone("7859632148");
        userRequest.setRole("user");
        userRequest.setAddress("nagercoil");
        User user = new User();
        user.setId("user1");
        user.setFirstName("Ajay");
        user.setLastName("Kumar");
        user.setUserName("ajay123");
        user.setEmail("ajay@gmail.com");
        user.setPassword("Ajay@123");
        user.setPhone("7859632148");
        user.setRole("user");
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId("user1");
        userProfile.setAddress("nagercoil");
        userProfile.setFailedLoginAttempts(0);
        userProfile.setIsAccountLocked(false);
        userProfile.setLockedTime(null);
        when(userRepository.existsByUserName("ajay123")).thenReturn(false);
        when(userRepository.existsByEmail("ajay@gmail.com")).thenReturn(false);
        when(userRepository.existsByPhone("7859632148")).thenReturn(false);
        when(userMapper.toEntity(userRequest)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserProfile(userRequest, "user1")).thenReturn(userProfile);
        User result = userService.registerUser(userRequest);
        assertNotNull(result);
        assertEquals("ajay@gmail.com", result.getEmail());
        assertEquals("ajay123", result.getUserName());
        verify(userRepository).existsByUserName("ajay123");
        verify(userRepository).existsByEmail("ajay@gmail.com");
        verify(userRepository).existsByPhone("7859632148");
        verify(userMapper).toEntity(userRequest);
        verify(userRepository).save(any(User.class));
        verify(userMapper).toUserProfile(userRequest, "user1");
        verify(userProfileRepository).save(any(UserProfile.class));
        verify(userLogService).createUserLog(eq("user1"), eq("register"), eq("success"));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        userRequest.setEmail("uma@gmail.com");
        when(userRepository.existsByEmail(anyString()))
                .thenReturn(true);
        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(userRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenPhoneAlreadyExists() {
        userRequest.setPhone("7859632141");
        when(userRepository.existsByPhone(anyString()))
                .thenReturn(true);
        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(userRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        userRequest.setUserName("ajay123");
        when(userRepository.existsByUserName(anyString()))
                .thenReturn(true);
        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(userRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldLoginUser() {
        loginRequest.setUserName("ajay123");
        loginRequest.setPassword("Ajay@123");
        User user = new User();
        user.setId("123");
        user.setUserName("ajay123");
        user.setEmail("ajay@gmail.com");
        user.setPassword("encodedPassword");
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId("123");
        userProfile.setIsAccountLocked(false);
        userProfile.setFailedLoginAttempts(0);
        userProfile.setLockedTime(null);
        when(userRepository.findByLoginUser("ajay123")).thenReturn(Optional.of(user));
        when(userProfileRepository.findByUserId("123")).thenReturn(Optional.of(userProfile));
        when(bCryptPasswordEncoder.matches("Ajay@123", "encodedPassword")).thenReturn(true);
        when(otpService.generateOtp()).thenReturn("123456");
        when(userSessionService.createUserSession(anyString(), anyString())).thenReturn(new UserSession());
        doNothing().when(emailQueueService).addToQueue(anyString(), anyString(), anyString());
        String result = userService.loginUser(loginRequest);
        assertEquals("Otp send successfully", result);
        verify(userSessionService, times(1)).createUserSession("123", "123456");
        verify(userLogService, times(1)).createUserLog(eq("123"), eq("login"), eq("success"));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        loginRequest.setUserName("ajay123");
        when(userRepository.findByLoginUser(anyString()))
                .thenReturn(Optional.empty());
        assertThrows(InvalidCredentialsException.class, () -> userService.loginUser(loginRequest));
    }

    @Test
    void shouldVerifyOtp() {
        VerifyOtpRequest verifyOtpRequest = new VerifyOtpRequest();
        verifyOtpRequest.setEmail("ajay@gmail.com");
        verifyOtpRequest.setOtp("123456");
        User user = new User();
        user.setId("123");
        user.setUserName("ajay123");
        user.setEmail("ajay@gmail.com");
        when(userRepository.findByEmail("ajay@gmail.com")).thenReturn(Optional.of(user));
        when(tokenUtility.generateJwt("ajay123")).thenReturn("jwt-token");
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Map<String, String> result = userService.verifyLoginOtp(verifyOtpRequest);
        assertEquals("jwt-token", result.get("accessToken"));
        assertNotNull(result.get("refreshKey"));
        verify(userSessionService, times(1)).updateSessionAfterOtp(anyString(), anyString(), anyString());
        verify(userLogService, times(1)).createUserLog(anyString(), eq("Verify Otp"), eq("success"));
    }

    @Test
    void shouldGenerateAccessToken() {
        String refreshKey = "refresh-123";
        UserSession userSession = new UserSession();
        userSession.setId("session1");
        userSession.setUserId("123");
        userSession.setRefreshKey(refreshKey);
        userSession.setRefreshKeyStatus(true);
        userSession.setSessionStatus("login");
        userSession.setRefreshKeyExpiration(LocalDateTime.now().plusDays(1));
        User user = new User();
        user.setId("123");
        user.setUserName("ajya123");
        when(userSessionService.getRefreshKey(refreshKey)).thenReturn(userSession);
        when(userRepository.findById("123")).thenReturn(Optional.of(user));
        when(tokenUtility.generateJwt(anyString())).thenReturn("new-jwt-token");
        String result = userService.generateAccessToken(refreshKey);
        assertEquals("new-jwt-token", result);
        verify(userSessionService).getRefreshKey(refreshKey);
        verify(userRepository).findById("123");
        verify(tokenUtility).generateJwt(anyString());
        System.out.println(userSession.getUserId());
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        User loggedUser = new User();
        loggedUser.setId("123");
        User user = new User();
        user.setFirstName("Ajay");
        user.setLastName("Kumar");
        user.setUserName("ajay123");
        user.setEmail("ajay@gmail.com");
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);
        try (MockedStatic<Apputility> mockedStatic = Mockito.mockStatic(Apputility.class)) {
            mockedStatic.when(Apputility::getLoggedUser).thenReturn(loggedUser);
            List<UserDto> result = userService.getAllUsers();
            assertEquals(1, result.size());
            assertEquals("Ajay", result.get(0).getFirstName());
            assertEquals("Kumar", result.get(0).getLastName());
            assertEquals("ajay@gmail.com", result.get(0).getEmail());
        }
    }

    @Test
    public void shouldSignOutUser() {
        String authHeader = "Bearer jwt-token";
        User loggedUser = new User();
        loggedUser.setId("123");
        loggedUser.setUserName("ajay123");
        UserSession userSession = new UserSession();
        userSession.setSessionStatus("logout");
        when(userRepository.findByUserName("ajay123"))
                .thenReturn(loggedUser);
        when(userSessionRepository.findTopByUserIdOrderByCreatedAtDesc("123")).thenReturn(userSession);
        try (MockedStatic<Apputility> mockedStatic = Mockito.mockStatic(Apputility.class)) {
            mockedStatic.when(Apputility::getLoggedUser).thenReturn(loggedUser);

            String result = userService.signout(authHeader);
            assertEquals("user logout successfully", result);
        }
        verify(redisTemplate, times(1)).delete("jwt-token");
        verify(userSessionRepository).save(userSession);
        verify(userLogService).createUserLog(eq("123"), eq("logout"), eq("success"));
    }

}
