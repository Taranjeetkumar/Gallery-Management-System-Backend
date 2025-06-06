package com.gallery.controller;

import com.gallery.dto.StaffRequest;
import com.gallery.dto.StaffResponse;
import com.gallery.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER')")
    public List<StaffResponse> getAllStaff() {
        return staffService.getAllStaff();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER')")
    public StaffResponse getStaffById(@PathVariable Long id) {
        return staffService.getStaffById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StaffResponse createStaff(@Valid @RequestBody StaffRequest request) {
        return staffService.createStaff(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public StaffResponse updateStaff(@PathVariable Long id, @Valid @RequestBody StaffRequest request) {
        return staffService.updateStaff(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
    }
}
