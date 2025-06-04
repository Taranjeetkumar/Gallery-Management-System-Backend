package com.gallery.service;

import com.gallery.dto.SignUpRequest;
import com.gallery.dto.LoginRequest;
import com.gallery.dto.AuthResponse;
import com.gallery.model.Role;
import com.gallery.model.User;
import com.gallery.repository.UserRepository;
import com.gallery.repository.RoleRepository;
import com.gallery.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }
         // Look up role from payload
        String requestedRole = signUpRequest.getRole(); // e.g., "ROLE_GALLERY_MANAGER"
        Role userRole = roleRepository.findByName(Role.ERole.valueOf(requestedRole))
        .orElseThrow(() -> new RuntimeException("Role not found in DB: " + requestedRole));

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setFullname(signUpRequest.getFullname());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
    }

    @Transactional
    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByUsername(loginRequest.getEmail())
            .orElseThrow(() -> new RuntimeException("No such user!"));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList());
        String token = jwtTokenProvider.createToken(userDetails, roles);
        return new AuthResponse(token, user.getUsername(), roles);
    }

    @Transactional
    public AuthResponse getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()
            || authentication.getPrincipal().equals("anonymousUser")) {
        throw new RuntimeException("Unauthorized access"); // Avoid using ResponseEntity in service
    }
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername(); // use getUsername(), not getEmail()
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("No such user!"));

    List<String> roles = user.getRoles().stream()
            .map(role -> role.getName().name())
            .toList();

    return new AuthResponse(null, username, roles);
    }
    

    public AuthResponse refreshToken(String refreshToken) {
        // For now, just validate the current token and issue a new one
        // In a real implementation, you'd have a separate refresh token mechanism
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // List<String> roles = Collections.singletonList(user.getRole());

          List<String> roles = user.getRoles().stream()
            .map(role -> role.getName().name())
            .toList();

        String newToken = jwtTokenProvider.createToken(
            org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
            .map(role -> role.getName().name())
            .toString())
                .build(),
            roles
        );

        return new AuthResponse(newToken, user.getUsername(), roles);

    }

    public void changePassword(String currentPassword, String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Unauthorized access");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

}
