package com.gallery.controller;

import com.gallery.dto.ContactFormRequest;
import com.gallery.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ContactFormController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/contactus")
    public ResponseEntity<Map<String, String>> submitContactForm(@Valid @RequestBody ContactFormRequest request) {
        try {
            emailService.sendContactFormEmail(
                request.getFullName(),
                request.getEmail(),
                request.getSubject(),
                request.getMessage()
            );

            Map<String, String> response = new HashMap<>();
            response.put("message", "Your message has been sent successfully! We'll get back to you soon.");
            response.put("status", "success");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Failed to send message. Please try again later.");
            response.put("status", "error");

            return ResponseEntity.status(500).body(response);
        }
    }
}
