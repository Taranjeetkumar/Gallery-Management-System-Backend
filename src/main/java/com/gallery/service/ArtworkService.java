package com.gallery.service;

import com.gallery.dto.ArtworkRequest;
import com.gallery.dto.ArtworkResponse;
import com.gallery.enums.ArtworkStatus;
import com.gallery.model.Artwork;
import com.gallery.model.Gallery;
import com.gallery.model.User;
import com.gallery.repository.ArtworkRepository;
import com.gallery.repository.GalleryRepository;
import com.gallery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GalleryRepository galleryRepository;

    public List<ArtworkResponse> getAllArtworks(Long artistId, Long galleryId) {

        List<Artwork> artworks;

    if (artistId != null && galleryId != null) {
        artworks = artworkRepository.findByArtistIdAndGalleryId(artistId, galleryId);
    } else if (artistId != null) {
        artworks = artworkRepository.findByArtistId(artistId);
    } else if (galleryId != null) {
        artworks = artworkRepository.findByGalleryId(galleryId);
    } else {
        artworks = artworkRepository.findAll();
    }

    return artworks.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());

    }
    

    public ArtworkResponse getArtworkById(Long id) {
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artwork not found with id: " + id));
        return mapToResponse(artwork);
    }

    public ArtworkResponse createArtwork(ArtworkRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        User artist = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
       
        Artwork artwork = new Artwork();
        artwork.setTitle(request.getTitle());
        artwork.setDescription(request.getDescription());
        artwork.setArtist(artist);

                        System.out.println("sdfghfsd  dsdds "+request.getGalleryId());

        if(request.getGalleryId() > 0){
            Gallery gallery = galleryRepository.findById(request.getGalleryId())
                .orElseThrow(() -> new RuntimeException("Gallery not found with id: " + request.getGalleryId()));
            artwork.setGallery(gallery);
        }
         
        artwork.setImageUrl(request.getImageUrl());
        artwork.setThumbnailUrl(request.getThumbnailUrl());
        artwork.setMedium(request.getMedium());
        artwork.setDimensions(request.getDimensions());
        artwork.setYearCreated(request.getYearCreated());
        artwork.setPrice(request.getPrice());
        artwork.setIsForSale(request.getIsForSale() != null ? request.getIsForSale() : false);
        artwork.setStatus(request.getStatus() != null ? ArtworkStatus.valueOf(request.getStatus()) : ArtworkStatus.ACTIVE);
        artwork.setIsFeatured(request.getIsFeatured() != null ? request.getIsFeatured() : false);

        Artwork saved = artworkRepository.save(artwork);
        return mapToResponse(saved);
    }

    public ArtworkResponse updateArtwork(Long id, ArtworkRequest request) {

                                System.out.println("gherdg. "+id);

        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artwork not found with id: " + id));
        if (request.getTitle() != null) artwork.setTitle(request.getTitle());
        if (request.getDescription() != null) artwork.setDescription(request.getDescription());
        if (request.getGalleryId() > 0) {
            Gallery gallery = galleryRepository.findById(request.getGalleryId())
                    .orElseThrow(() -> new RuntimeException("Gallery not found with id: " + request.getGalleryId()));
            artwork.setGallery(gallery);
        }
        if (request.getImageUrl() != null) artwork.setImageUrl(request.getImageUrl());
        if (request.getThumbnailUrl() != null) artwork.setThumbnailUrl(request.getThumbnailUrl());
        artwork.setMedium(request.getMedium());
        artwork.setDimensions(request.getDimensions());
        artwork.setYearCreated(request.getYearCreated());
        if (request.getPrice() != null) artwork.setPrice(request.getPrice());
        if (request.getIsForSale() != null) artwork.setIsForSale(request.getIsForSale());
        if (request.getStatus() != null) artwork.setStatus(ArtworkStatus.valueOf(request.getStatus()));
        if (request.getIsFeatured() != null) artwork.setIsFeatured(request.getIsFeatured());

        Artwork updated = artworkRepository.save(artwork);
        return mapToResponse(updated);
    }

    public void deleteArtwork(Long id) {
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artwork not found with id: " + id));
        artworkRepository.delete(artwork);
    }

    private ArtworkResponse mapToResponse(Artwork artwork) {
        ArtworkResponse response = new ArtworkResponse();
        response.setId(artwork.getId());
        response.setTitle(artwork.getTitle());
        response.setDescription(artwork.getDescription());
        if (artwork.getGallery() != null) {
        response.setGalleryId(artwork.getGallery().getId());
    }

    if (artwork.getArtist() != null) {
        response.setArtistId(artwork.getArtist().getId());
    }

     if (artwork.getArtist() != null) {
            response.setCreatedByArtistName(artwork.getArtist().getFullname());
        }
        response.setImageUrl(artwork.getImageUrl());
        response.setThumbnailUrl(artwork.getThumbnailUrl());
        response.setMedium(artwork.getMedium());
        response.setDimensions(artwork.getDimensions());
        response.setYearCreated(artwork.getYearCreated());
        response.setPrice(artwork.getPrice());
        response.setIsForSale(artwork.getIsForSale());
        response.setStatus(artwork.getStatus().name());
        response.setIsFeatured(artwork.getIsFeatured());
        response.setCreatedAt(artwork.getCreatedAt());
        response.setUpdatedAt(artwork.getUpdatedAt());
        return response;
    }
}
