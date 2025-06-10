package com.gallery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import com.gallery.dto.OpeningHoursDTO;
import com.gallery.dto.SocialMediaDTO;
import lombok.Data;


@Data
public class GalleryRequest {
    @NotBlank
    private String name;
    private String description;
    private String address;
    private String city;
    private String state;
    private String title;
    private String zipCode;
    private String country;
    private String phone;
    private String imageUrl;
    @Email
    private String email;
    private String website;
    private OpeningHoursDTO openingHours;
    private SocialMediaDTO socialMedia;

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public OpeningHoursDTO getOpeningHours() { return openingHours; }
    public void setOpeningHours(OpeningHoursDTO openingHours) { this.openingHours = openingHours; }
    public SocialMediaDTO getSocialMedia() { return socialMedia; }
    public void setSocialMedia(SocialMediaDTO socialMedia) { this.socialMedia = socialMedia; }
}
