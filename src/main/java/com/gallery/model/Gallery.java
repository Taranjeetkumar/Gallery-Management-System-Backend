package com.gallery.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "galleries")
public class Gallery {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(length = 255)
    private String description;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
