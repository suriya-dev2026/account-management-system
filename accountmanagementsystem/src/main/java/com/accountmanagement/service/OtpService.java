package com.accountmanagement.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender javaMailSender;

    private Map<String, String> otpStorage = new HashMap<>();

    private Map<String, LocalDateTime> otpExpiry = new HashMap<>();

    public String generateOtp() {
        return String.valueOf((int) ((Math.random() * 900000) + 100000));
    }

    public void sendOtp(String email) {
        String otp = generateOtp();
        otpStorage.put(email, otp);
        otpExpiry.put(email, LocalDateTime.now().plusMinutes(2));
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Login OTP");
        simpleMailMessage.setText("Your OTP is " + otp +
                "\n\nOTP valid for 120 seconds.");
        javaMailSender.send(simpleMailMessage);
    }

    public boolean verifyOtp(String email, String userOtp) {
        String savedOtp = otpStorage.get(email);
        if (savedOtp == null) {
            throw new RuntimeException("OTP not found");
        }

        LocalDateTime expiryTime = otpExpiry.get(email);
        if (expiryTime.isBefore(LocalDateTime.now())) {
            otpStorage.remove(email);
            otpExpiry.remove(email);
            throw new RuntimeException("OTP expired");
        }

        if (!savedOtp.equals(userOtp)) {
            throw new RuntimeException("Invalid Otp");
        }
        otpStorage.remove(email);
        otpExpiry.remove(email);

        return true;
    }
}
