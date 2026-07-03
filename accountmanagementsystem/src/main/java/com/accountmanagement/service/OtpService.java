package com.accountmanagement.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.User;
import com.accountmanagement.model.UserSession;
import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.repository.UserSessionRepository;

@Service
public class OtpService {

    private final UserRepository userRepository;

    private final UserSessionRepository userSessionRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final SecureRandom random = new SecureRandom();

    OtpService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
            UserSessionRepository userSessionRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userSessionRepository = userSessionRepository;
    }

    public String generateOtp() {
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void verifyOtp(String email, String enteredOtp) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException("Email not found"));

        UserSession session = userSessionRepository.findTopByUserIdOrderByCreatedAtDesc(user.getId());
        if (session == null) {
            throw new RecordNotFoundException("session not found");
        }
        if (session.getOtp() == null) {
            throw new RuntimeException("Otp not found");
        }
        if (session.getOtpVerificationCount() >= 3) {
            throw new RuntimeException("Maximum attempts reached");
        }
        if (LocalDateTime.now().isAfter(session.getOtpExpiration())) {
            throw new RuntimeException("OTP expired");
        }
        if (!bCryptPasswordEncoder.matches(enteredOtp, session.getOtp())) {
            session.setOtpVerificationCount(session.getOtpVerificationCount() + 1);
            userSessionRepository.save(session);
            throw new RuntimeException("Invalid Otp");
        }
        session.setIsOtpVerified(true);
        session.setOtpVerificationCount(0);
        userSessionRepository.save(session);
    }

}
