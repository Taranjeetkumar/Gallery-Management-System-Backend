package com.gallery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.gallery.dto.OpeningHoursDTO;
import com.gallery.dto.SocialMediaDTO;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GalleryResponse {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private String ownerUsername;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String phone;
    private String email;
    private String website;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OpeningHoursDTO openingHours;
    private SocialMediaDTO socialMedia;
}
