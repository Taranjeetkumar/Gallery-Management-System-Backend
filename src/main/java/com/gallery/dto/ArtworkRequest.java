package com.gallery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ArtworkRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Long galleryId;

    @NotBlank
    private String imageUrl;

    private String thumbnailUrl;

    private String medium;

    private String dimensions;

    private Integer yearCreated;

    @PositiveOrZero
    private BigDecimal price;

    private Boolean isForSale;

    private String status;  // Should match ArtworkStatus enum name

    private Boolean isFeatured;
}
