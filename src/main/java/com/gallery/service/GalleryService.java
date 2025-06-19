package com.gallery.service;

import com.gallery.dto.GalleryRequest;
import com.gallery.dto.GalleryResponse;
import com.gallery.model.Gallery;
import com.gallery.model.User;
import com.gallery.repository.GalleryRepository;
import com.gallery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GalleryService {
    @Autowired
    private GalleryRepository galleryRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Get all galleries. Accessible by USER and ADMIN.
     */
    public List<GalleryResponse> getAllGalleries() {
        return galleryRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get a gallery by its ID. Accessible by USER and ADMIN.
     */
    public GalleryResponse getGalleryById(Long id) {
        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gallery not found"));
        return toResponse(gallery);
    }

    /**
     * Create a new gallery. Accessible by ADMIN only.
     */
    public GalleryResponse createGallery(GalleryRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Gallery gallery = new Gallery();
        gallery.setTitle(request.getTitle());
        gallery.setDescription(request.getDescription());
        gallery.setImageUrl(request.getImageUrl());
        gallery.setOwner(owner);
        galleryRepository.save(gallery);
        return toResponse(gallery);
    }

    /**
     * Update an existing gallery. Accessible by ADMIN only.
     */
    public GalleryResponse updateGallery(Long id, GalleryRequest request) {
        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gallery not found"));
        gallery.setTitle(request.getTitle());
        gallery.setDescription(request.getDescription());
        gallery.setImageUrl(request.getImageUrl());
        galleryRepository.save(gallery);
        return toResponse(gallery);
    }

    /**
     * Delete a gallery. Accessible by ADMIN only.
     */
    public void deleteGallery(Long id) {
        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gallery not found"));
        galleryRepository.delete(gallery);
    }

    /**
     * Map Gallery entity to GalleryResponse DTO.
     */
    private GalleryResponse toResponse(Gallery gallery) {
        return new GalleryResponse(
                gallery.getId(),
                gallery.getTitle(),
                gallery.getDescription(),
                gallery.getImageUrl(),
                gallery.getOwner().getUsername()
        );
    }
}
