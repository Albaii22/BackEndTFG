package com.tfg.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.backend.entities.User;

@Repository
// Repository interface for managing User entities
public interface UserRepository extends JpaRepository<User, Long> {
    // Finds a user by username
    Optional<User> findByUsername(String username);
    
    // Finds a user by email
    Optional<User> findByEmail(String email);
}
