package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JWTService jwtService;

    public void generateAndSendOtp(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("This email is not authorized for admin access."));

        String otp = String.format("%06d", new Random().nextInt(999999));
        user.setOtp(otp);
        user.setOtpExpiry(System.currentTimeMillis() + (10 * 60 * 1000)); // 10 minutes
        userRepository.save(user);

        emailService.sendOtpEmail(email, otp);
    }

    public String verifyOtpAndGenerateToken(String email, String otp) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getOtp() != null && user.getOtp().equals(otp) && user.getOtpExpiry() > System.currentTimeMillis()) {
            // Success: Clear OTP and generate JWT
            user.setOtp(null);
            user.setOtpExpiry(null);
            userRepository.save(user);
            return jwtService.generateToken(email);
        } else {
            throw new RuntimeException("Invalid or expired OTP");
        }
    }
}
