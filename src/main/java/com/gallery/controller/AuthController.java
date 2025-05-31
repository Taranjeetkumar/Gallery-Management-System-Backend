package com.gallery.controller;

import com.gallery.dto.SignUpRequest;
import com.gallery.dto.LoginRequest;
import com.gallery.dto.AuthResponse;
import com.gallery.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.registerUser(signUpRequest);
        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse response = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> getCurrentUser() {
        AuthResponse response = authService.getCurrentUser();
        return ResponseEntity.ok(response);    
}

}
