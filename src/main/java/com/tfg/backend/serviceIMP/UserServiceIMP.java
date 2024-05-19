package com.tfg.backend.serviceIMP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tfg.backend.entities.User;
import com.tfg.backend.repository.UserRepository;
import com.tfg.backend.service.UserService;

import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceIMP implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceIMP.class);

    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            logger.error("Could not create upload directory", e);
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    @Override
    public List<User> getAllUsuarios() {
        try {
            return userRepository.findAll();
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve all users", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<User> getUsuarioById(Long id) {
        try {
            return userRepository.findById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to fetch user by ID: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public User createUsuario(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            logger.error("Username already exists: {}", user.getUsername());
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            logger.error("Email already exists: {}", user.getEmail());
            throw new IllegalArgumentException("Email already exists");
        }

        try {
            return userRepository.save(user);
        } catch (DataAccessException e) {
            logger.error("Failed to create user", e);
            throw e;
        }
    }

    @Override
    public User updateUsuario(Long id, User user) {
        try {
            user.setId(id);
            return userRepository.save(user);
        } catch (DataAccessException e) {
            logger.error("Failed to update user with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public void deleteUsuario(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to delete user with ID: {}", id, e);
            throw e;
        }
    }

    /**
     * Obtiene el ID del usuario por nombre de usuario.
     *
     * @param username el nombre de usuario
     * @return el ID del usuario
     */
    public Optional<Long> getUsuarioIdByUsername(String username) {
        try {
            Optional<User> user = userRepository.findByUsername(username);
            return user.map(User::getId);
        } catch (DataAccessException e) {
            logger.error("Failed to fetch user by username: {}", username, e);
            return Optional.empty();
        }
    }

    @Override
    public User uploadProfileImage(Long id, MultipartFile file) throws IOException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            String filename = file.getOriginalFilename();
            Path path = Paths.get(uploadDir + filename);
            Files.write(path, file.getBytes());

            user.setProfileImageUrl(path.toString());

            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
}

