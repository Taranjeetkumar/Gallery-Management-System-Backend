package com.gallery.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.gallery.dto.SocialMediaDTO;

@Data
public class ArtistRequest {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(min = 3, max = 50)
    private String fullname;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;
    private SocialMediaDTO socialMedia;

    private String phone;
  public SocialMediaDTO getSocialMedia() { return socialMedia; }
    public void setSocialMedia(SocialMediaDTO socialMedia) { this.socialMedia = socialMedia; }

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @Size(max = 100)
    private String avatar;

    @Size(max = 100)
    private String birthplace;

    private Integer age;

    @Size(max = 100)
    private String artisticStyle;
}
