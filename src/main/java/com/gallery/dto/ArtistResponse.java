package com.gallery.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;
import com.gallery.dto.SocialMediaDTO;

@Data
public class ArtistResponse {
    private Long id;
    private String username;
    private String fullname;
    private String email;
    private String avatar;
    private String birthplace;
    private Integer age;
    private String artisticStyle;
    private Set<String> roles;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
     private long artworkCount;
    private String createdByUsername;
    private SocialMediaDTO socialMedia;


    public long getArtworkCount() {
        return artworkCount;
    }

    public void setArtworkCount(long artworkCount) {
        this.artworkCount = artworkCount;
    }

     public String getCreatedByUsername() {
        return createdByUsername;
    }

    public void setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
    }
}
