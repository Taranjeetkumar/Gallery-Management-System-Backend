package com.gallery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GalleryResponse {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private String ownerUsername;
}
