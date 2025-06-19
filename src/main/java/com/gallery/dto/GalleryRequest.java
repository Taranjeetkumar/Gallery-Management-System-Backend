package com.gallery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GalleryRequest {
    @NotBlank @Size(max = 100)
    private String title;
    @Size(max = 255)
    private String description;
    @Size(max = 255)
    private String imageUrl;
}
