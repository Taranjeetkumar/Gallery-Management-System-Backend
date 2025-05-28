package com.gallery.controller;

import com.gallery.dto.GalleryRequest;
import com.gallery.dto.GalleryResponse;
import com.gallery.service.GalleryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/galleries")
public class GalleryController {
    @Autowired
    private GalleryService galleryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<GalleryResponse> getAllGalleries() {
        return galleryService.getAllGalleries();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public GalleryResponse getGalleryById(@PathVariable UUID id) {
        return galleryService.getGalleryById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public GalleryResponse createGallery(@Valid @RequestBody GalleryRequest request) {
        return galleryService.createGallery(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GalleryResponse updateGallery(@PathVariable UUID id, @Valid @RequestBody GalleryRequest request) {
        return galleryService.updateGallery(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteGallery(@PathVariable UUID id) {
        galleryService.deleteGallery(id);
    }
}
