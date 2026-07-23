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
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
import com.accountmanagement.enums.Gender;
import com.accountmanagement.exceptions.InvalidCredentialsException;
import com.accountmanagement.exceptions.UserAlreadyExistsException;
import com.accountmanagement.mapper.UserMapper;
import com.accountmanagement.model.EmailQueue;
import com.accountmanagement.model.User;
import com.accountmanagement.model.UserProfile;
import com.accountmanagement.model.UserSession;
import com.accountmanagement.repository.UserProfileRepository;
import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.repository.UserSessionRepository;
import com.accountmanagement.request.LoginRequest;
import com.accountmanagement.request.UserRegistrationRequest;
import com.accountmanagement.request.VerifyOtpRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.utility.TokenUtility;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserLoginAuditLogService userLoginAuditLogService;

    @Mock
    private OtpService otpService;

    @Mock
    private UserSessionService userSessionService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private TokenUtility tokenUtility;

    @Mock
    private ValueOperations<String, String> valueOperation;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

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

    UserRegistrationRequest userRequest = new UserRegistrationRequest();

    @Test
    void shouldRegisterUser() {
        UUID organizationId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        userRequest.setFirstName("Ajay");
        userRequest.setLastName("Kumar");
        userRequest.setUserName(("ajay123"));
        userRequest.setEmail("ajay@gmail.com");
        userRequest.setPassword("Ajay@123");
        userRequest.setConfirmPassword("Ajay@123");
        userRequest.setContactNumber("7859632148");
        userRequest.setGender(Gender.Male);
        userRequest.setAddress("Nagercoil");
        userRequest.setUserType("Staff");
        User user = new User();
        user.setId(userId);
        user.setOrganizationId(organizationId);
        user.setUserName("ajay123");
        user.setEmail("ajay@gmail.com");
        user.setPassword("Ajay@123");
        user.setContactNumber("7859632148");
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(user.getId());
        userProfile.setAddress("nagercoil");
        when(userRepository.existsByUserName("ajay123")).thenReturn(false);
        when(userRepository.existsByEmail("ajay@gmail.com")).thenReturn(false);
        when(userRepository.existsByContactNumber("7859632148")).thenReturn(false);
        when(userMapper.toRegisterUser(userRequest)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserProfile(userRequest, userId)).thenReturn(userProfile);
        User result = userService.registerUser(userRequest);
        assertNotNull(result);
        assertEquals("ajay@gmail.com", result.getEmail());
        assertEquals("ajay123", result.getUserName());
        verify(userRepository).existsByUserName("ajay123");
        verify(userRepository).existsByEmail("ajay@gmail.com");
        verify(userRepository).existsByContactNumber("7859632148");
        verify(userMapper).toRegisterUser(userRequest);
        verify(userRepository).save(any(User.class));
        verify(userMapper).toUserProfile(userRequest, user.getId());
        verify(userProfileRepository).save(any(UserProfile.class));
        verify(userLoginAuditLogService).createUserLog(organizationId, userId, "register", "success");
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
        userRequest.setContactNumber("7859632141");
        when(userRepository.existsByContactNumber(anyString()))
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
        LoginRequest loginRequest = new LoginRequest();
        UUID userId = UUID.randomUUID();
        UUID organizationId = UUID.randomUUID();
        loginRequest.setLogin("ajay123");
        loginRequest.setPassword("Ajay@123");
        User user = new User();
        user.setId(userId);
        user.setOrganizationId(organizationId);
        user.setUserName("ajay123");
        user.setEmail("ajay@gmail.com");
        user.setPassword("encodedPassword");
        user.setFailedLoginAttempts(0);
        user.setIsAccountLocked(false);
        user.setLockedTime(null);
        UserSession userSession = new UserSession();
        EmailQueue emailQueue = new EmailQueue();
        when(userRepository.findByUserNameOrEmailOrContactNumber(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches("Ajay@123", "encodedPassword")).thenReturn(true);
        when(otpService.generateOtp()).thenReturn("123456");
        when(userSessionService.createUserSession(userId, "123456")).thenReturn(userSession);
        when(emailQueueService.addToQueue(userId, "ajay@gmail.com")).thenReturn(emailQueue);
        doNothing().when(otpService).sendEmail(emailQueue, "123456");
        String result = userService.loginUser(loginRequest);
        assertEquals("Otp send successfully", result);
        verify(userRepository).findByUserNameOrEmailOrContactNumber("ajay123", "ajay123", "ajay123");
        verify(bCryptPasswordEncoder).matches("Ajay@123", "encodedPassword");
        verify(otpService).generateOtp();
        verify(userSessionService).createUserSession(userId, "123456");
        verify(emailQueueService).addToQueue(userId, "ajay@gmail.com");
        verify(otpService).sendEmail(emailQueue, "123456");
        verify(userLoginAuditLogService).createUserLog(organizationId, userId, "Login", "Success");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin("ajay123");
        when(userRepository.findByUserNameOrEmailOrContactNumber("ajay123", "ajay123", "ajay123"))
                .thenReturn(Optional.empty());
        assertThrows(InvalidCredentialsException.class, () -> userService.loginUser(loginRequest));
    }

    @Test
    void shouldVerifyOtp() {
        UUID userId = UUID.randomUUID();
        UUID organizationID = UUID.randomUUID();
        VerifyOtpRequest verifyOtpRequest = new VerifyOtpRequest();
        verifyOtpRequest.setEmail("ajay@gmail.com");
        verifyOtpRequest.setOtp("123456");
        User user = new User();
        user.setId(userId);
        user.setOrganizationId(organizationID);
        user.setUserName("ajay123");
        user.setEmail("ajay@gmail.com");
        when(userRepository.findByEmail("ajay@gmail.com")).thenReturn(Optional.of(user));
        doNothing().when(otpService).verifyOtp(userId, "123456");
        when(tokenUtility.generateJwt("ajay123")).thenReturn("jwt-token");
        when(redisTemplate.opsForValue()).thenReturn(valueOperation);
        ApiResponse response = userService.verifyLoginOtp(verifyOtpRequest);
        assertNotNull(response);
        assertEquals("jwt-token", response.getAccessToken());
        assertNotNull(response.getRefreshKey());
        verify(userRepository).findByEmail("ajay@gmail.com");
        verify(otpService).verifyOtp(userId, "123456");
        verify(tokenUtility).generateJwt("ajay123");
        verify(valueOperation).set(eq("jwt-token"), eq(userId.toString()), eq(10L), eq(TimeUnit.MINUTES));
        verify(userSessionService).updateSessionAfterOtp(eq(userId), anyString());
        verify(userLoginAuditLogService).createUserLog(organizationID, userId, "Verify Otp", "Success");
    }

    @Test
    void shouldGenerateAccessToken() {
        String refreshKey = "refresh-123";
        UUID userId = UUID.randomUUID();
        UUID sessionId = UUID.randomUUID();
        UserSession userSession = new UserSession();
        userSession.setId(sessionId);
        userSession.setUserId(userId);
        userSession.setRefreshKey(refreshKey);
        userSession.setRefreshKeyStatus(true);
        userSession.setSessionStatus("login");
        userSession.setRefreshKeyExpiration(LocalDateTime.now().plusDays(1));
        User user = new User();
        user.setId(userId);
        user.setUserName("ajay123");
        when(userSessionRepository.findByRefreshKey(refreshKey)).thenReturn(Optional.of(userSession));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(tokenUtility.generateJwt("ajay123"))
                .thenReturn("new-jwt-token");
        when(redisTemplate.opsForValue())
                .thenReturn(valueOperation);
        String result = userService.generateAccessToken(refreshKey);

        assertEquals("new-jwt-token", result);

        verify(userSessionRepository).findByRefreshKey(refreshKey);
        verify(userRepository).findById(userId);
        verify(tokenUtility).generateJwt("ajay123");

        verify(valueOperation).set("new-jwt-token", userId.toString(), 10L, TimeUnit.MINUTES);
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        User loggedUser = new User();
        // loggedUser.setId();
        User user = new User();
        user.setUserName("ajay123");
        user.setEmail("ajay@gmail.com");
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);
        try (MockedStatic<Apputility> mockedStatic = Mockito.mockStatic(Apputility.class)) {
            mockedStatic.when(Apputility::getLoggedUser).thenReturn(loggedUser);
            List<User> result = userService.getAllUsers();
            assertEquals(1, result.size());
            assertEquals("ajay@gmail.com", result.get(0).getEmail());
        }
    }

    @Test
    public void shouldSignOutUser() {
        String authHeader = "Bearer jwt-token";
        UUID userId = UUID.randomUUID();
        UUID organizationId = UUID.randomUUID();
        User loggedUser = new User();
        loggedUser.setId(userId);
        loggedUser.setUserName("ajay123");
        loggedUser.setOrganizationId(organizationId);
        UserSession userSession = new UserSession();
        userSession.setUserId(userId);
        userSession.setSessionStatus("Active");
        userSession.setRefreshKey("refresh-token");
        userSession.setRefreshKeyStatus(true);
        userSession.setIsValidToken(true);
        when(userSessionRepository.findTopByUserIdOrderByCreatedAtDesc(userId))
                .thenReturn(Optional.of(userSession));
        try (MockedStatic<Apputility> mockedStatic = Mockito.mockStatic(Apputility.class)) {
            mockedStatic.when(Apputility::getLoggedUser).thenReturn(loggedUser);

            String result = userService.signout(authHeader);
            assertEquals("user logout successfully", result);
        }
        verify(redisTemplate, times(1)).delete("jwt-token");
        verify(userSessionRepository).save(userSession);
        verify(userLoginAuditLogService).createUserLog(organizationId, userId, "logout", "success");
    }

}
