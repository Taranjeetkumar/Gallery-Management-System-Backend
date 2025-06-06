package com.gallery.dto;

import com.gallery.enums.ContactType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ContactResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private ContactType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
