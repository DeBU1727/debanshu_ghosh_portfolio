package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    private String role; // e.g., "ADMIN"

    private String otp;
    
    private Long otpExpiry;

    public User() {}

    public User(String email, String role) {
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }
    public Long getOtpExpiry() { return otpExpiry; }
    public void setOtpExpiry(Long otpExpiry) { this.otpExpiry = otpExpiry; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
