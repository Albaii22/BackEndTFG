package com.tfg.backend.service;

import com.tfg.backend.DTO.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsuarios();
    Optional<UserDTO> getUsuarioById(Long id);
    UserDTO createUsuario(UserDTO userDTO);
    UserDTO updateUsuario(Long id, UserDTO userDTO);
    void deleteUsuario(Long id);
    UserDTO uploadProfileImage(Long id, MultipartFile file) throws IOException;
    Optional<Long> getUsuarioIdByUsername(String username);
    UserDTO updateAboutMe(Long id, String aboutMe);
}
