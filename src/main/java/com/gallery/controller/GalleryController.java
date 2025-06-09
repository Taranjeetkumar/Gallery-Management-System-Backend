package com.gallery.controller;

import com.gallery.dto.GalleryRequest;
import com.gallery.dto.GalleryResponse;
import com.gallery.service.GalleryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/gallery")
@CrossOrigin(origins = "*")
public class GalleryController {
    @Autowired
    private GalleryService galleryService;

   @GetMapping
   @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER','USER','ARTIST')")
    public List<GalleryResponse> getAllGalleries(
         @RequestParam(value = "artistId", required = false) Long artistId,
        @RequestParam(value = "galleryId", required = false) Long galleryId) {

                    System.out.println("hffdfc ffd  "+artistId);

        return galleryService.getAllGalleries(artistId, galleryId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public GalleryResponse getGalleryById(@PathVariable Long id) {
        return galleryService.getGalleryById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public GalleryResponse createGallery(@Valid @RequestBody GalleryRequest request) {

        System.out.println("dfsggsdf  "+request);
        return galleryService.createGallery(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GalleryResponse updateGallery(@PathVariable Long id, @Valid @RequestBody GalleryRequest request) {
        return galleryService.updateGallery(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteGallery(@PathVariable Long id) {
        galleryService.deleteGallery(id);
    }
}
