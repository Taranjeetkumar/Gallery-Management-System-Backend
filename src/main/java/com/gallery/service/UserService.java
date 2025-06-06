package com.gallery.service;

import com.gallery.model.User;
import com.gallery.repository.UserRepository;
import com.gallery.dto.UserProfileRequest;
import com.gallery.dto.UserProfileResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    public UserProfileResponse updateProfile(UserProfileRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Unauthorized access");
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        // Update fields if provided
        if (request.getFullname() != null) user.setFullname(request.getFullname());
        if (request.getAvatar() != null) user.setAvatar(request.getAvatar());
        if (request.getBirthplace() != null) user.setBirthplace(request.getBirthplace());
        if (request.getAge() != null) user.setAge(request.getAge());
        if (request.getArtisticStyle() != null) user.setArtisticStyle(request.getArtisticStyle());
        User updated = userRepository.save(user);
        // Build response
        UserProfileResponse response = new UserProfileResponse();
        response.setId(updated.getId());
        response.setUsername(updated.getUsername());
        response.setFullname(updated.getFullname());
        response.setEmail(updated.getEmail());
        response.setAvatar(updated.getAvatar());
        response.setBirthplace(updated.getBirthplace());
        response.setAge(updated.getAge());
        response.setArtisticStyle(updated.getArtisticStyle());
        response.setRoles(updated.getRoles().stream().map(r -> r.getName().name()).collect(java.util.stream.Collectors.toSet()));
        response.setIsActive(updated.getIsActive());
        response.setCreatedAt(updated.getCreatedAt());
        response.setUpdatedAt(updated.getUpdatedAt());
        return response;
    }
}
