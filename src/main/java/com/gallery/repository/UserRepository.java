package com.gallery.repository;

import com.gallery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import com.gallery.model.Role;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<User> findByRolesName(Role.ERole name);
    List<User> findByCreatedByUsernameAndRolesName(String username, Role.ERole role);

}
