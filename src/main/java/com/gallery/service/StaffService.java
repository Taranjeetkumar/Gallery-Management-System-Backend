package com.gallery.service;

import com.gallery.dto.StaffRequest;
import com.gallery.dto.StaffResponse;
import com.gallery.model.Role;
import com.gallery.model.User;
import com.gallery.repository.RoleRepository;
import com.gallery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StaffService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<StaffResponse> getAllStaff() {
        return userRepository.findByRolesName(Role.ERole.ROLE_GALLERY_MANAGER).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public StaffResponse getStaffById(Long id) {
        User staff = userRepository.findById(id)
                .filter(u -> u.getRoles().stream().anyMatch(r -> r.getName() == Role.ERole.ROLE_GALLERY_MANAGER))
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));
        return mapToResponse(staff);
    }

    public StaffResponse createStaff(StaffRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }
        Role staffRole = roleRepository.findByName(Role.ERole.ROLE_GALLERY_MANAGER)
                .orElseThrow(() -> new RuntimeException("Staff role not found"));
        User staff = new User();
        staff.setUsername(request.getUsername());
        staff.setFullname(request.getFullname());
        staff.setEmail(request.getEmail());
        staff.setPassword(passwordEncoder.encode(request.getPassword()));
        staff.setAvatar(request.getAvatar());
        staff.setSpecializationArtType(request.getSpecializationArtType());
        staff.setSpecializationStyle(request.getSpecializationStyle());
        staff.setRoles(Set.of(staffRole));
        User saved = userRepository.save(staff);
        return mapToResponse(saved);
    }

    public StaffResponse updateStaff(Long id, StaffRequest request) {
        User staff = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));
        staff.setFullname(request.getFullname());
        if (request.getAvatar() != null) staff.setAvatar(request.getAvatar());
        staff.setSpecializationArtType(request.getSpecializationArtType());
        staff.setSpecializationStyle(request.getSpecializationStyle());
        User updated = userRepository.save(staff);
        return mapToResponse(updated);
    }

    public void deleteStaff(Long id) {
        User staff = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));
        userRepository.delete(staff);
    }

    private StaffResponse mapToResponse(User user) {
        StaffResponse response = new StaffResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setFullname(user.getFullname());
        response.setEmail(user.getEmail());
        response.setAvatar(user.getAvatar());
        response.setSpecializationArtType(user.getSpecializationArtType());
        response.setSpecializationStyle(user.getSpecializationStyle());
        response.setRoles(user.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toSet()));
        response.setIsActive(user.getIsActive());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
