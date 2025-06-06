package com.gallery.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

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
}
