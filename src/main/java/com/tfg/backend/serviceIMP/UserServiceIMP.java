package com.tfg.backend.serviceIMP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tfg.backend.DTO.PublicationsDTO;
import com.tfg.backend.DTO.UserDTO;
import com.tfg.backend.DTO.CommentsDTO;
import com.tfg.backend.entities.Comments;
import com.tfg.backend.entities.Publications;
import com.tfg.backend.entities.User;
import com.tfg.backend.repository.UserRepository;
import com.tfg.backend.service.UserService;

import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<UserDTO> getAllUsuarios() {
        try {
            logger.info("Fetching all users");
            List<User> users = userRepository.findAll();
            logger.info("Number of users fetched: {}", users.size());
            return users.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve all users", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<UserDTO> getUsuarioById(Long id) {
        try {
            logger.info("Fetching user with ID: {}", id);
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                logger.info("User found: {}", user.get());
                return Optional.of(convertToDTO(user.get()));
            } else {
                logger.warn("User with ID: {} not found", id);
                return Optional.empty();
            }
        } catch (DataAccessException e) {
            logger.error("Failed to fetch user by ID: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public UserDTO createUsuario(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            logger.error("Username already exists: {}", userDTO.getUsername());
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            logger.error("Email already exists: {}", userDTO.getEmail());
            throw new IllegalArgumentException("Email already exists");
        }

        try {
            User user = convertToEntity(userDTO);
            user = userRepository.save(user);
            return convertToDTO(user);
        } catch (DataAccessException e) {
            logger.error("Failed to create user", e);
            throw e;
        }
    }

    @Override
    public UserDTO updateUsuario(Long id, UserDTO userDTO) {
        try {
            User user = convertToEntity(userDTO);
            user.setId(id);
            user = userRepository.save(user);
            return convertToDTO(user);
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
    public UserDTO uploadProfileImage(Long id, MultipartFile file) throws IOException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            String filename = file.getOriginalFilename();
            Path path = Paths.get(uploadDir + filename);
            Files.write(path, file.getBytes());

            user.setProfileImageUrl(path.toString());
            user = userRepository.save(user);

            return convertToDTO(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .profileImageUrl(user.getProfileImageUrl())
                .registration_date(new Date(user.getRegistrationDate().getTime()))
                .publications(user.getPublications().stream().map(this::convertPublicationToDTO).collect(Collectors.toList()))
                .build();
    }

    private User convertToEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId() != null ? Long.parseLong(userDTO.getId()) : null)
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .profileImageUrl(userDTO.getProfileImageUrl())
                .registrationDate(new Date(userDTO.getRegistration_date().getTime()))
                .build();
    }

    private PublicationsDTO convertPublicationToDTO(Publications publication) {
        return PublicationsDTO.builder()
                .id(publication.getId())
                .content(publication.getContent())
                .timestamp(publication.getTimestamp())
                .user_id(publication.getUser().getId().intValue())
                .vote_count(publication.getVoteCount())
                .comments(publication.getComments() != null ? 
                          publication.getComments().stream().map(this::convertCommentToDTO).collect(Collectors.toList()) 
                          : Collections.emptyList())
                .build();
    }

    private CommentsDTO convertCommentToDTO(Comments comment) {
        return CommentsDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .timestamp(comment.getTimestamp())
                .userId(comment.getUser().getId())
                .publicationId(comment.getPublication().getId())
                .build();
    }
}
