package com.gallery.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserProfileRequest {
    @Size(min = 3, max = 50)
    private String fullname;
    private String avatar;
    private String birthplace;
    private Integer age;
    private String artisticStyle;
}
