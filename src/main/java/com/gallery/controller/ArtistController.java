package com.gallery.controller;

import com.gallery.dto.ArtistRequest;
import com.gallery.dto.ArtistResponse;
import com.gallery.service.ArtistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
@CrossOrigin(origins = "*")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER','GALLERY_MANAGER','ARTIST')")
    public List<ArtistResponse> getAllArtists() {
        return artistService.getAllArtists();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','GALLERY_MANAGER','ARTIST')")
    public ArtistResponse getArtist(@PathVariable Long id) {
        return artistService.getArtistById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER')")
    public ArtistResponse createArtist(@Valid @RequestBody ArtistRequest request) {
        return artistService.createArtist(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER')")
    public ArtistResponse updateArtist(@PathVariable Long id, @Valid @RequestBody ArtistRequest request) {
        return artistService.updateArtist(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER')")
    public void deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
    }
}
