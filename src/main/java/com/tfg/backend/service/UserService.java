package com.tfg.backend.service;

import com.tfg.backend.entities.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    List<User> getAllUsuarios();
    Optional<User> getUsuarioById(Long id);
    User createUsuario(User user);
    User updateUsuario(Long id, User user);
    void deleteUsuario(Long id);
    Optional<Long> getUsuarioIdByUsername(String username);
    User uploadProfileImage(Long id, MultipartFile file) throws IOException;
}
