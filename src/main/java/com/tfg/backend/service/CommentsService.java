package com.tfg.backend.service;

import com.tfg.backend.DTO.CommentsDTO;

import java.util.List;
import java.util.Optional;

// Service interface for managing Comments
public interface CommentsService {
    // Retrieves all comments
    List<CommentsDTO> getAllComentarios();
    
    // Retrieves a comment by ID
    Optional<CommentsDTO> getComentarioById(Long id);
    
    // Creates a new comment for a given user ID
    CommentsDTO createComentario(CommentsDTO commentsDTO, Long userId);
    
    // Updates an existing comment
    CommentsDTO updateComentario(Long id, CommentsDTO commentsDTO);
    
    // Deletes a comment by ID
    void deleteComentario(Long id);
}
