package com.gallery.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ArtworkResponse {
    private Long id;
    private String title;
    private String description;
    private Long galleryId;
    private Long artistId;
    private String imageUrl;
    private String thumbnailUrl;
    private String medium;
    private String dimensions;
    private Integer yearCreated;
    private BigDecimal price;
    private Boolean isForSale;
    private String status;
    private Boolean isFeatured;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
