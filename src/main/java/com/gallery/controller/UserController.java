package com.gallery.controller;

import com.gallery.dto.UserProfileRequest;
import com.gallery.dto.UserProfileResponse;
import com.gallery.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public UserProfileResponse getProfile() {
        // Reuse updateProfile logic: treat getProfile similar to updateProfile with no changes
        return userService.updateProfile(new UserProfileRequest());
    }

    @PutMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public UserProfileResponse updateProfile(@Valid @RequestBody UserProfileRequest request) {
        return userService.updateProfile(request);
    }
}
