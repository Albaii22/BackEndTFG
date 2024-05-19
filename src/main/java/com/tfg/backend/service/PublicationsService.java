package com.tfg.backend.service;

import com.tfg.backend.DTO.PublicationsDTO;
import java.util.List;
import java.util.Optional;

public interface PublicationsService {
    List<PublicationsDTO> getAllPublicaciones();
    Optional<PublicationsDTO> getPublicacionById(Long id);
    PublicationsDTO save(PublicationsDTO publicationDTO, Long userId);
    PublicationsDTO updatePublicacion(Long id, PublicationsDTO publicationDTO);
    void deletePublicacion(Long id);
}
