package com.gallery.service;

import com.gallery.dto.ArtistRequest;
import com.gallery.dto.ArtistResponse;
import com.gallery.model.Role;
import com.gallery.model.User;
import com.gallery.repository.RoleRepository;
import com.gallery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gallery.dto.SocialMediaDTO;
import com.gallery.model.SocialMedia;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<ArtistResponse> getAllArtists() {
        return userRepository.findByRolesName(Role.ERole.ROLE_ARTIST).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ArtistResponse getArtistById(Long id) {
        User artist = userRepository.findById(id)
                .filter(u -> u.getRoles().stream().anyMatch(r -> r.getName() == Role.ERole.ROLE_ARTIST))
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + id));
        return mapToResponse(artist);
    }

    public ArtistResponse createArtist(ArtistRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }
        Role artistRole = roleRepository.findByName(Role.ERole.ROLE_ARTIST)
                .orElseThrow(() -> new RuntimeException("Artist role not found"));
        User artist = new User();
        artist.setUsername(request.getUsername());
        artist.setFullname(request.getFullname());
        artist.setEmail(request.getEmail());
        artist.setPhone(request.getPhone());
        artist.setPassword(passwordEncoder.encode(request.getPassword()));
        artist.setAvatar(request.getAvatar());
        artist.setBirthplace(request.getBirthplace());
        artist.setAge(request.getAge());
        artist.setArtisticStyle(request.getArtisticStyle());
        artist.setRoles(Set.of(artistRole));

        SocialMedia sm = new SocialMedia();
        sm.setInstagram(request.getSocialMedia().getInstagram());
        sm.setFacebook(request.getSocialMedia().getFacebook());
        sm.setWebsite(request.getSocialMedia().getWebsite());
        sm.setTwitter(request.getSocialMedia().getTwitter());
        artist.setSocialMedia(sm);

        User saved = userRepository.save(artist);
        return mapToResponse(saved);
    }

    public ArtistResponse updateArtist(Long id, ArtistRequest request) {
        User artist = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + id));
        // Update basic and artist fields
        artist.setFullname(request.getFullname());
        artist.setAvatar(request.getAvatar());
        artist.setBirthplace(request.getBirthplace());
        artist.setAge(request.getAge());
        artist.setArtisticStyle(request.getArtisticStyle());
        User updated = userRepository.save(artist);
        return mapToResponse(updated);
    }

    public void deleteArtist(Long id) {
        User artist = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + id));
        userRepository.delete(artist);
    }

    private ArtistResponse mapToResponse(User user) {
        ArtistResponse response = new ArtistResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setFullname(user.getFullname());
        response.setEmail(user.getEmail());
        response.setAvatar(user.getAvatar());
        response.setBirthplace(user.getBirthplace());
        response.setAge(user.getAge());
        response.setArtisticStyle(user.getArtisticStyle());
        response.setRoles(user.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toSet()));
        response.setIsActive(user.getIsActive());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
