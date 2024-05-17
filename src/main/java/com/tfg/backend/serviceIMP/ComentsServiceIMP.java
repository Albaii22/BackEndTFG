package com.tfg.backend.serviceIMP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.tfg.backend.entities.Comments;
import com.tfg.backend.repository.ComentsRepository;
import com.tfg.backend.service.ComentsService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ComentsServiceIMP implements ComentsService {

    private static final Logger logger = LoggerFactory.getLogger(ComentsServiceIMP.class);

    @Autowired
    private ComentsRepository comentsRepository;

    @Override
    public List<Comments> getAllComentarios() {
        try {
            return comentsRepository.findAll();
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve all comments", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Comments> getComentarioById(Long id) {
        try {
            return comentsRepository.findById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to fetch comment by ID: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public Comments createComentario(Comments comments) {
        try {
            return comentsRepository.save(comments);
        } catch (DataAccessException e) {
            logger.error("Failed to create comment: {}", comments, e);
            throw e;
        }
    }

    @Override
    public Comments updateComentario(Long id, Comments comments) {
        try {
            comments.setId(id);
            return comentsRepository.save(comments);
        } catch (DataAccessException e) {
            logger.error("Failed to update comment with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public void deleteComentario(Long id) {
        try {
            comentsRepository.deleteById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to delete comment with ID: {}", id, e);
            throw e;
        }
    }
}
