package com.gallery.controller;

import com.gallery.dto.GalleryRequest;
import com.gallery.dto.GalleryResponse;
import com.gallery.service.GalleryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

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
        @RequestParam(value = "galleryId", required = false) Long galleryId,
        @RequestParam(value = "filters", required = false) Map<String, String> filters,
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "limit", defaultValue = "12") int limit ){
        
        System.out.println("hffdfc ffd  "+filters);

        return galleryService.getAllGalleries(filters,artistId, galleryId,page,limit);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public GalleryResponse getGalleryById(@PathVariable Long id) {
        return galleryService.getGalleryById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER')")
    public GalleryResponse createGallery(@Valid @RequestBody GalleryRequest request) {

        System.out.println("dfsggsdf  "+request);
        return galleryService.createGallery(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER')")
    public GalleryResponse updateGallery(@PathVariable Long id, @Valid @RequestBody GalleryRequest request) {
        return galleryService.updateGallery(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER')")
    public void deleteGallery(@PathVariable Long id) {
                System.out.println("lhghjfjvfjv ::  "+id);
         galleryService.deleteGallery(id);
    }
}
