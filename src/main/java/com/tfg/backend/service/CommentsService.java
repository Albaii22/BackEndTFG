package com.tfg.backend.service;

import com.tfg.backend.DTO.CommentsDTO;

import java.util.List;
import java.util.Optional;

public interface CommentsService {
    List<CommentsDTO> getAllComentarios();
    Optional<CommentsDTO> getComentarioById(Long id);
    CommentsDTO createComentario(CommentsDTO commentsDTO);
    CommentsDTO updateComentario(Long id, CommentsDTO commentsDTO);
    void deleteComentario(Long id);
}
