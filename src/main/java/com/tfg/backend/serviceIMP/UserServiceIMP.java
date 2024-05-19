package com.tfg.backend.serviceIMP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.tfg.backend.entities.User;
import com.tfg.backend.repository.UserRepository;
import com.tfg.backend.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceIMP implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceIMP.class);

    @Autowired
    private UserRepository userRepository;

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
}

