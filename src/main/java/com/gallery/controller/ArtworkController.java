package com.gallery.controller;

import com.gallery.dto.ArtworkRequest;
import com.gallery.dto.ArtworkResponse;
import com.gallery.service.ArtworkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artworks")
@CrossOrigin(origins = "*")
public class ArtworkController {

    @Autowired
    private ArtworkService artworkService;

    // @GetMapping
    // @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER','USER','ARTIST')")
    // public List<ArtworkResponse> getAllArtworks() {
    //     return artworkService.getAllArtworks();
    // }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER','USER','ARTIST')")
    public List<ArtworkResponse> getAllArtworks(
        @RequestParam(value = "artistId", required = false) Long artistId,
        @RequestParam(value = "galleryId", required = false) Long galleryId) {
    return artworkService.getAllArtworks(artistId, galleryId);
}


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER','USER','ARTIST')")
    public ArtworkResponse getArtwork(@PathVariable Long id) {
        return artworkService.getArtworkById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER','ARTIST')")
    public ArtworkResponse createArtwork(@Valid @RequestBody ArtworkRequest request) {

                System.out.println("dfsggsdf  dsdds "+request);

        return artworkService.createArtwork(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER','ARTIST')")
    public ArtworkResponse updateArtwork(@PathVariable Long id, @Valid @RequestBody ArtworkRequest request) {
                        System.out.println("fgxfxfgg. "+request);

        return artworkService.updateArtwork(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER')")
    public void deleteArtwork(@PathVariable Long id) {
        artworkService.deleteArtwork(id);
    }
}
