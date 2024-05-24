package com.tfg.backend.service;

import com.tfg.backend.DTO.PublicationsDTO;
import java.util.List;
import java.util.Optional;

// Service interface for managing Publications
public interface PublicationsService {
    // Retrieves all publications
    List<PublicationsDTO> getAllPublicaciones();
    
    // Retrieves a publication by ID
    Optional<PublicationsDTO> getPublicacionById(Long id);
    
    // Saves a new publication for a given user ID
    PublicationsDTO save(PublicationsDTO publicationDTO, Long userId);
    
    // Updates an existing publication
    PublicationsDTO updatePublicacion(Long id, PublicationsDTO publicationDTO);
    
    // Deletes a publication by ID
    void deletePublicacion(Long id);
    
    // Retrieves all publications for a given user ID
    List<PublicationsDTO> getPublicacionesByUserId(Long userId);
}
