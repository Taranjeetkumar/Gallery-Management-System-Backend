package com.gallery.repository;

import com.gallery.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    List<Artwork> findByArtistId(Long artistId);

    List<Artwork> findByGalleryId(Long galleryId);

    List<Artwork> findByArtistIdAndGalleryId(Long artistId, Long galleryId);

    long countByArtistId(Long artistId);
}
