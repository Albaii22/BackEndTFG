package com.tfg.backend.serviceIMP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.tfg.backend.DTO.PublicationsDTO;
import com.tfg.backend.DTO.CommentsDTO;
import com.tfg.backend.entities.Comments;
import com.tfg.backend.entities.Publications;
import com.tfg.backend.entities.User;
import com.tfg.backend.repository.PublicationsRepository;
import com.tfg.backend.repository.UserRepository;
import com.tfg.backend.service.PublicationsService;

import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicationServiceIMP implements PublicationsService {
    private static final Logger logger = LoggerFactory.getLogger(PublicationServiceIMP.class);

    @Autowired
    private PublicationsRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<PublicationsDTO> getAllPublicaciones() {
        try {
            List<Publications> publications = publicationRepository.findAll();
            return publications.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve all publications", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<PublicationsDTO> getPublicacionById(Long id) {
        try {
            return publicationRepository.findById(id).map(this::convertToDTO);
        } catch (DataAccessException e) {
            logger.error("Failed to fetch publication by ID: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public PublicationsDTO save(PublicationsDTO publicationDTO, Long userId) {
        try {
            Publications publication = convertToEntity(publicationDTO, userId);
            publication = publicationRepository.save(publication);
            return convertToDTO(publication);
        } catch (DataAccessException e) {
            logger.error("Failed to create publication", e);
            throw e;
        }
    }

    @Override
    public PublicationsDTO updatePublicacion(Long id, PublicationsDTO publicationDTO) {
        try {
            Publications existingPublication = publicationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Publication not found"));
            existingPublication.setContent(publicationDTO.getContent());
            existingPublication = publicationRepository.save(existingPublication);
            return convertToDTO(existingPublication);
        } catch (DataAccessException e) {
            logger.error("Failed to update publication with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public void deletePublicacion(Long id) {
        try {
            publicationRepository.deleteById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to delete publication with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<PublicationsDTO> getPublicacionesByUserId(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("No user found with ID: " + userId));
            List<Publications> publications = publicationRepository.findByUser(user);
            return publications.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve publications for user ID: {}", userId, e);
            return Collections.emptyList();
        }
    }

    public void toggleLike(Long publicationId, Long userId) {
        try {
            Publications publication = publicationRepository.findById(publicationId)
                    .orElseThrow(() -> new IllegalArgumentException("Publication not found"));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            if (publication.getLikedBy().contains(user)) {
                publication.getLikedBy().remove(user);
                publication.setVote_count(publication.getVote_count() - 1);
            } else {
                publication.getLikedBy().add(user);
                publication.setVote_count(publication.getVote_count() + 1);
            }

            publicationRepository.save(publication);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error toggling like", e);
        }
    }

    private PublicationsDTO convertToDTO(Publications publication) {
        return PublicationsDTO.builder()
                .id(publication.getId())
                .content(publication.getContent())
                .timestamp(publication.getTimestamp())
                .user_id(publication.getUser().getId().intValue())
                .vote_count(publication.getVote_count())
                .likedBy(publication.getLikedBy() != null ? 
                    publication.getLikedBy().stream().map(User::getId).collect(Collectors.toSet()) : 
                    Collections.emptySet())
                .comments(publication.getComments() != null
                        ? publication.getComments().stream().map(this::convertCommentToDTO).collect(Collectors.toList())
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

    private Publications convertToEntity(PublicationsDTO dto, Long userId) {
        Publications publication = new Publications();
        if (dto.getId() != null) {
            publication.setId(dto.getId());
        }
        publication.setContent(dto.getContent());
        publication.setTimestamp(new Date());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No user found with ID: " + userId));
        publication.setUser(user);
        publication.setLikedBy(new HashSet<>());

        return publication;
    }
}
