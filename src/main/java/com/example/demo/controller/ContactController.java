package com.example.demo.controller;

import com.example.demo.model.ContactMessage;
import com.example.demo.repository.ContactMessageRepository;
import com.example.demo.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
public class ContactController {

    @Autowired
    private ContactMessageRepository contactRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<?> submitContactMessage(@Valid @RequestBody ContactMessage message) {
        try {
            // 1. Save to database
            ContactMessage savedMessage = contactRepository.save(message);

            // 2. Send email notification asynchronously (or synchronously for now)
            emailService.sendContactEmail(
                savedMessage.getName(),
                savedMessage.getEmail(),
                savedMessage.getMessage()
            );

            return ResponseEntity.ok().body("{\"message\": \"Message sent successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"Failed to send message: " + e.getMessage() + "\"}");
        }
    }
}
