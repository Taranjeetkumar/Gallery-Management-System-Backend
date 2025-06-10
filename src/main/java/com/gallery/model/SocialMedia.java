package com.gallery.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class SocialMedia {

    private String instagram;
    private String facebook;
    private String twitter;
    private String website;


    // getters and setters
    public String getInstagram() { return instagram; }
    public void setInstagram(String instagram) { this.instagram = instagram; }
    public String getTwitter() { return twitter; }
    public void setTwitter(String twitter) { this.twitter = twitter; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public String getFacebook() { return facebook; }
    public void setFacebook(String facebook) { this.facebook = facebook; }
}
