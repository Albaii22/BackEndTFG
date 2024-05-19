package com.tfg.backend.serviceIMP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.tfg.backend.DTO.PublicationsDTO;
import com.tfg.backend.entities.Publications;
import com.tfg.backend.entities.User;
import com.tfg.backend.repository.PublicationsRepository;
import com.tfg.backend.repository.UserRepository;
import com.tfg.backend.service.PublicationsService;

import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.Date;
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

    private PublicationsDTO convertToDTO(Publications publication) {
        PublicationsDTO dto = new PublicationsDTO();
        dto.setId(publication.getId());
        dto.setContent(publication.getContent());
        dto.setTimestamp(publication.getTimestamp());
        return dto;
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
    
        return publication;
    }
    

}
