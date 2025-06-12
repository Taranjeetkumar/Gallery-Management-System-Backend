package com.gallery.service;

import com.gallery.dto.GalleryRequest;
import com.gallery.dto.GalleryResponse;
import com.gallery.dto.OpeningHoursDTO;
import com.gallery.dto.SocialMediaDTO;
import com.gallery.model.Gallery;
import com.gallery.model.User;
import java.util.Map;

import com.gallery.model.OpeningHours;
import com.gallery.model.SocialMedia;
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
        public List<GalleryResponse> getAllGalleries(Map<String, String> filters, Long artistId, Long galleryId, int page, int limit) {
        List<Gallery> galleries;
        // apply artistId/galleryId filtering at repository level when possible
        if (artistId != null && galleryId != null) {
            galleries = galleryRepository.findByOwnerIdAndId(artistId, galleryId);
        } else if (artistId != null) {
            galleries = galleryRepository.findByOwner_Id(artistId);
        } else if (galleryId != null) {
            galleries = galleryRepository.findById(galleryId)
                .map(List::of)
                .orElse(List.of());
        } else {
           
            galleries =  galleryRepository.findAll();
        }
        // Apply filters if provided

        System.out.println("jdhjkcvhj : "+filters);
        if (filters != null) {
            if (filters.containsKey("city") && !filters.get("city").isBlank()) {
                String city = filters.get("city");
                galleries = galleries.stream()
                    .filter(g -> city.equalsIgnoreCase(g.getCity()))
                    .toList();
            }
            if (filters.containsKey("state") && !filters.get("state").isBlank()) {
                String state = filters.get("state");
                galleries = galleries.stream()
                    .filter(g -> state.equalsIgnoreCase(g.getState()))
                    .toList();
            }
            if (filters.containsKey("country") && !filters.get("country").isBlank()) {
                String country = filters.get("country");
                galleries = galleries.stream()
                    .filter(g -> country.equalsIgnoreCase(g.getCountry()))
                    .toList();
            }
        }
        // Map to response
        return galleries.stream()
            .map(this::toResponse)
            .toList();
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
        gallery.setName(request.getName());
        gallery.setDescription(request.getDescription());
        gallery.setAddress(request.getAddress());
                gallery.setImageUrl(request.getImageUrl());
        gallery.setCity(request.getCity());
                gallery.setOwner(owner);

        gallery.setState(request.getState());
        gallery.setZipCode(request.getZipCode());
        gallery.setCountry(request.getCountry());
        gallery.setPhone(request.getPhone());
        gallery.setEmail(request.getEmail());
        gallery.setWebsite(request.getWebsite());
        OpeningHours oh = new OpeningHours();
        oh.setMonday(request.getOpeningHours().getMonday());
        oh.setTuesday(request.getOpeningHours().getTuesday());
        oh.setWednesday(request.getOpeningHours().getWednesday());
        oh.setThursday(request.getOpeningHours().getThursday());
        oh.setFriday(request.getOpeningHours().getFriday());
        oh.setSaturday(request.getOpeningHours().getSaturday());
        oh.setSunday(request.getOpeningHours().getSunday());
        gallery.setOpeningHours(oh);
        SocialMedia sm = new SocialMedia();
        sm.setInstagram(request.getSocialMedia().getInstagram());
        sm.setFacebook(request.getSocialMedia().getFacebook());
        gallery.setSocialMedia(sm);
        Gallery saved = galleryRepository.save(gallery);
       GalleryResponse response = new GalleryResponse(
            saved.getId(),
            saved.getTitle(),
            saved.getDescription(),
            saved.getImageUrl(),
            saved.getOwner().getUsername(),
            saved.getName(),
            saved.getAddress(),
            saved.getCity(),
            saved.getState(),
            saved.getZipCode(),
            saved.getCountry(),
            saved.getPhone(),
            saved.getEmail(),
            saved.getWebsite(),
             saved.getCreatedAt(),
            saved.getUpdatedAt(),
            request.getOpeningHours(),
            request.getSocialMedia()
        );
        return response;
    }

    /**
     * Update an existing gallery. Accessible by ADMIN only.
     */
    public GalleryResponse updateGallery(Long id, GalleryRequest request) {
        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gallery not found"));
        gallery.setName(request.getName());
        gallery.setDescription(request.getDescription());
        gallery.setAddress(request.getAddress());
        gallery.setCity(request.getCity());
        gallery.setState(request.getState());
        gallery.setZipCode(request.getZipCode());
        gallery.setCountry(request.getCountry());
        gallery.setPhone(request.getPhone());
        gallery.setEmail(request.getEmail());
        gallery.setWebsite(request.getWebsite());
        OpeningHours oh = gallery.getOpeningHours() != null ? gallery.getOpeningHours() : new OpeningHours();
        OpeningHoursDTO ohDto = request.getOpeningHours();
        oh.setMonday(ohDto.getMonday());
        oh.setTuesday(ohDto.getTuesday());
        oh.setWednesday(ohDto.getWednesday());
        oh.setThursday(ohDto.getThursday());
        oh.setFriday(ohDto.getFriday());
        oh.setSaturday(ohDto.getSaturday());
        oh.setSunday(ohDto.getSunday());
        gallery.setOpeningHours(oh);
        SocialMedia sm = gallery.getSocialMedia() != null ? gallery.getSocialMedia() : new SocialMedia();
        SocialMediaDTO smDto = request.getSocialMedia();
        sm.setInstagram(smDto.getInstagram());
        sm.setFacebook(smDto.getFacebook());
        gallery.setSocialMedia(sm);
        Gallery updated = galleryRepository.save(gallery);

return new GalleryResponse(
            updated.getId(),
            updated.getTitle(),
            updated.getDescription(),
            updated.getImageUrl(),
            updated.getOwner().getUsername(),
            updated.getName(),
            updated.getAddress(),
            updated.getCity(),
            updated.getState(),
            updated.getZipCode(),
            updated.getCountry(),
            updated.getPhone(),
            updated.getEmail(),
            updated.getWebsite(),
               updated.getCreatedAt(),
            updated.getUpdatedAt(),
            request.getOpeningHours(),
            request.getSocialMedia()
        );
    }

    /**
     * Delete a gallery. Accessible by ADMIN only.
     */
    public void deleteGallery(Long id) {
        System.out.println("ghjhvh ::  "+id);
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
            gallery.getOwner().getUsername(),
            gallery.getName(),
            gallery.getAddress(),
            gallery.getCity(),
            gallery.getState(),
            gallery.getZipCode(),
            gallery.getCountry(),
            gallery.getPhone(),
            gallery.getEmail(),
            gallery.getWebsite(),
            gallery.getCreatedAt(),
            gallery.getUpdatedAt(),
            mapOpeningHours(gallery.getOpeningHours()),
            mapSocialMedia(gallery.getSocialMedia())
        );
    }

    private OpeningHoursDTO mapOpeningHours(OpeningHours oh) {
        if (oh == null) return null;
        OpeningHoursDTO dto = new OpeningHoursDTO();
        dto.setMonday(oh.getMonday()); dto.setTuesday(oh.getTuesday());
        dto.setWednesday(oh.getWednesday()); dto.setThursday(oh.getThursday());
        dto.setFriday(oh.getFriday()); dto.setSaturday(oh.getSaturday());
        dto.setSunday(oh.getSunday());
        return dto;
    }

    private SocialMediaDTO mapSocialMedia(SocialMedia sm) {
        if (sm == null) return null;
        SocialMediaDTO dto = new SocialMediaDTO();
        dto.setInstagram(sm.getInstagram()); dto.setFacebook(sm.getFacebook());
        return dto;
    }
}
