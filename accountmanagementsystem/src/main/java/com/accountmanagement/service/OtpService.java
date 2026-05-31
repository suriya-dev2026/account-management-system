package com.accountmanagement.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.User;
import com.accountmanagement.model.UserSession;
import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.repository.UserSessionRepository;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    private static final SecureRandom random = new SecureRandom();

    public String generateOtp() {
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public String sendOtp(String email, String otp) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Login OTP");
        simpleMailMessage.setText("Your OTP is " + otp +
                "\n\nOTP valid for 120 seconds.");
        javaMailSender.send(simpleMailMessage);
        return "otp send successfully";
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
        if (!session.getOtp().equals(enteredOtp)) {
            session.setOtpVerificationCount(session.getOtpVerificationCount() + 1);
            userSessionRepository.save(session);
            throw new RuntimeException("Invalid Otp");
        }
        session.setIsOtpVerified(true);
        session.setOtpVerificationCount(0);
        userSessionRepository.save(session);
    }

}
