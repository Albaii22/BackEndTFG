package com.tfg.backend.serviceIMP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tfg.backend.entities.Publications;
import com.tfg.backend.repository.PublicationsRepository;
import com.tfg.backend.service.PublicationsService;

import java.util.List;
import java.util.Optional;

@Service
public class PublicationServiceIMP implements PublicationsService {

    @Autowired
    private PublicationsRepository publicationRepository;

    @Override
    public List<Publications> getAllPublicaciones() {
        return publicationRepository.findAll();
    }

    @Override
    public Optional<Publications> getPublicacionById(Long id) {
        return publicationRepository.findById(id);
    }

    @Override
    public Publications createPublicacion(Publications publication) {
        return publicationRepository.save(publication);
    }

    @Override
    public Publications updatePublicacion(Long id, Publications publication) {
        publication.setId(id);
        return publicationRepository.save(publication);
    }

    @Override
    public void deletePublicacion(Long id) {
        publicationRepository.deleteById(id);
    }
}
