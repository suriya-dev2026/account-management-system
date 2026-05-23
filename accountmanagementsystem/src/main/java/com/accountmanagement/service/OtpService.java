package com.accountmanagement.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.User;
import com.accountmanagement.repository.UserRepository;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    public String generateOtp() {
        return String.valueOf((int) ((Math.random() * 9000) + 1000));
    }

    public void sendOtp(User user) {
        String otp = generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(2));
        userRepository.save(user);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Login OTP");
        simpleMailMessage.setText("Your OTP is " + otp +
                "\n\nOTP valid for 120 seconds.");
        javaMailSender.send(simpleMailMessage);
    }

    public void verifyOtp(String email, String enteredOtp) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException("Email not found"));
        if (user.getOtp() == null) {
            throw new RecordNotFoundException("Otp not found");
        }
        if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RecordNotFoundException("otp expired");
        }
        if (!user.getOtp().equals(enteredOtp)) {
            throw new RecordNotFoundException("Invalid otp");
        }
        user.setOtp(null);
        user.setOtpExpiry(null);
        userRepository.save(user);
    }

}
