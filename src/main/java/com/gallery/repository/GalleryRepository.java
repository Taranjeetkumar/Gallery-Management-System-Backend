package com.gallery.repository;

import com.gallery.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface GalleryRepository extends JpaRepository<Gallery, UUID> {
    List<Gallery> findByOwner_Id(UUID ownerId);
}
