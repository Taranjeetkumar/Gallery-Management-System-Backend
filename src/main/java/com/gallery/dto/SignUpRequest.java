package com.gallery.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank @Size(min = 3, max = 50)
    private String username;
    @NotBlank @Size(min = 3, max = 50)
    private String fullname;
    @NotBlank @Size(min = 3, max = 50)
    private String role;
    @NotBlank @Email @Size(max = 100)
    private String email;
    @NotBlank @Size(min = 6, max = 40)
    private String password;
}
