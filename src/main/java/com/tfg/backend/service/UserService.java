package com.tfg.backend.service;

import com.tfg.backend.DTO.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

// Service interface for managing Users
public interface UserService {
    // Retrieves all users
    List<UserDTO> getAllUsuarios();
    
    // Retrieves a user by ID
    Optional<UserDTO> getUsuarioById(Long id);
    
    // Creates a new user
    UserDTO createUsuario(UserDTO userDTO);
    
    // Updates an existing user
    UserDTO updateUsuario(Long id, UserDTO userDTO);
    
    // Deletes a user by ID
    void deleteUsuario(Long id);
    
    // Uploads a profile image for a user
    UserDTO uploadProfileImage(Long id, MultipartFile file) throws IOException;
    
    // Retrieves the ID of a user by username
    Optional<Long> getUsuarioIdByUsername(String username);
    
    // Updates the "about me" section for a user
    UserDTO updateAboutMe(Long id, String aboutMe);
}
