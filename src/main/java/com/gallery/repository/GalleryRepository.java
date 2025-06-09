package com.gallery.repository;

import com.gallery.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    List<Gallery> findByOwner_Id(Long ownerId);
    List<Gallery> findByOwnerIdAndId(Long ownerId, Long id);
}
