package com.gallery.enums;

/**
 * Artwork Status Enum - defines the status of artworks in the gallery system
 */
public enum ArtworkStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    SOLD("Sold"),
    RESERVED("Reserved"),
    ARCHIVED("Archived");

    private final String displayName;

    ArtworkStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}