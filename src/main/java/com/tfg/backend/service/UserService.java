package com.tfg.backend.service;

import com.tfg.backend.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsuarios();
    Optional<User> getUsuarioById(Long id);
    User createUsuario(User user);
    User updateUsuario(Long id, User user);
    void deleteUsuario(Long id);
    Optional<Long> getUsuarioIdByUsername(String username);
}
