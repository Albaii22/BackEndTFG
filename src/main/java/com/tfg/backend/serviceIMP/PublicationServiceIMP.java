package com.tfg.backend.serviceIMP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.tfg.backend.entities.Publications;
import com.tfg.backend.repository.PublicationsRepository;
import com.tfg.backend.service.PublicationsService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PublicationServiceIMP implements PublicationsService {

    private static final Logger logger = LoggerFactory.getLogger(PublicationServiceIMP.class);

    @Autowired
    private PublicationsRepository publicationRepository;

    @Override
    public List<Publications> getAllPublicaciones() {   
        try {
            return publicationRepository.findAll();
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve all publications", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Publications> getPublicacionById(Long id) {
        try {
            return publicationRepository.findById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to fetch publication by ID: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public Publications createPublicacion(Publications publication) {
        try {
            return publicationRepository.save(publication);
        } catch (DataAccessException e) {
            logger.error("Failed to create publication", e);
            throw e;
        }
    }

    @Override
    public Publications updatePublicacion(Long id, Publications publication) {
        try {
            publication.setId(id);
            return publicationRepository.save(publication);
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
}
