package com.gallery.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.gallery.enums.ArtworkStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.NotBlank;


@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "artworks")
public class Artwork {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Artwork title is required")
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Artist is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private User artist;

    @NotNull(message = "Gallery is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column
    private String medium;

    @Column
    private String dimensions;

    @Column(name = "year_created")
    private Integer yearCreated;

    @PositiveOrZero(message = "Price must be positive or zero")
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "is_for_sale")
    private Boolean isForSale = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArtworkStatus status = ArtworkStatus.ACTIVE;

    @Column(name = "view_count")
    private Long viewCount = 0L;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    // File metadata
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

     @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}