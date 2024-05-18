package com.tfg.backend.serviceIMP;

import com.tfg.backend.DTO.CommentsDTO;
import com.tfg.backend.entities.Comments;
import com.tfg.backend.entities.Publications;
import com.tfg.backend.entities.User;
import com.tfg.backend.repository.ComentsRepository;
import com.tfg.backend.repository.PublicationsRepository;
import com.tfg.backend.repository.UserRepository;
import com.tfg.backend.service.CommentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentsServiceIMP implements CommentsService {

    private static final Logger logger = LoggerFactory.getLogger(CommentsServiceIMP.class);

    @Autowired
    private ComentsRepository commentsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PublicationsRepository publicationsRepository;

    private final CommentsConverter commentsConverter = new CommentsConverter();

    @Override
    public List<CommentsDTO> getAllComentarios() {
        try {
            List<Comments> comments = commentsRepository.findAll();
            return comments.stream()
                    .map(commentsConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve all comments", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<CommentsDTO> getComentarioById(Long id) {
        try {
            return commentsRepository.findById(id)
                    .map(commentsConverter::convertToDTO);
        } catch (DataAccessException e) {
            logger.error("Failed to fetch comment by ID: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public CommentsDTO createComentario(CommentsDTO commentsDTO, Long userId) {
        try {
            // Verifica que userId y publicationId no sean null
            if (userId == null || commentsDTO.getPublicationId() == null) {
                throw new IllegalArgumentException("User ID and Publication ID must not be null");
            }

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            Publications publication = publicationsRepository.findById(commentsDTO.getPublicationId())
                    .orElseThrow(() -> new IllegalArgumentException("Publication not found"));

            Comments comment = commentsConverter.convertToEntity(commentsDTO, user, publication);
            comment = commentsRepository.save(comment);
            return commentsConverter.convertToDTO(comment);
        } catch (DataAccessException e) {
            logger.error("Failed to create comment: {}", commentsDTO, e);
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public CommentsDTO updateComentario(Long id, CommentsDTO commentsDTO) {
        try {
            return commentsRepository.findById(id)
                    .map(existingComment -> {
                        existingComment.setContent(commentsDTO.getContent());
                        existingComment.setTimestamp(commentsDTO.getTimestamp());
                        commentsRepository.save(existingComment);
                        return commentsConverter.convertToDTO(existingComment);
                    })
                    .orElseThrow(() -> new IllegalArgumentException("No comment found with ID: " + id));
        } catch (DataAccessException e) {
            logger.error("Failed to update comment with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public void deleteComentario(Long id) {
        try {
            commentsRepository.deleteById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to delete comment with ID: {}", id, e);
            throw e;
        }
    }

    private class CommentsConverter {

        public CommentsDTO convertToDTO(Comments comment) {
            return CommentsDTO.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .timestamp((java.sql.Date) comment.getTimestamp())
                    .userId(comment.getUser().getId())
                    .publicationId(comment.getPublication().getId())
                    .build();
        }

        public Comments convertToEntity(CommentsDTO commentDTO, User user, Publications publication) {
            Comments comment = new Comments();
            comment.setContent(commentDTO.getContent());
            comment.setTimestamp(commentDTO.getTimestamp());
            comment.setUser(user);
            comment.setPublication(publication);
            return comment;
        }
    }
}
