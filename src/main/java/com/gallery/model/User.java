package com.gallery.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_name"),
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 50)
    private String username;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullname;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "bio")
    private String bio;

    @Column(nullable = false)
    private String password;

    @Column(name = "avatar_url")
    private String avatar;

    @Column(name = "birthplace")
    private String birthplace;

    @Column(name = "age")
    private Integer age;

    @Column(name = "artistic_style")
    private String artisticStyle;

    @Column(name = "specialization_art_type")
    private String specializationArtType;

    @Column(name = "specialization_style")
    private String specializationStyle;

    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Embedded
    private SocialMedia socialMedia;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Gallery> galleries;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Artwork> artworks;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Role getRole() {
        return roles.stream().findFirst().orElse(null);
    }
}
