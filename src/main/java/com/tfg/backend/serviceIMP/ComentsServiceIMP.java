package com.tfg.backend.serviceIMP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tfg.backend.entities.Comments;
import com.tfg.backend.repository.ComentsRepository;
import com.tfg.backend.service.ComentsService;

import java.util.List;
import java.util.Optional;

@Service
public class ComentsServiceIMP implements ComentsService {

    @Autowired
    private ComentsRepository comentsRepository;

    @Override
    public List<Comments> getAllComentarios() {
        return comentsRepository.findAll();
    }

    @Override
    public Optional<Comments> getComentarioById(Long id) {
        return comentsRepository.findById(id);
    }

    @Override
    public Comments createComentario(Comments coments) {
        return comentsRepository.save(coments);
    }

    @Override
    public Comments updateComentario(Long id, Comments coments) {
        coments.setId(id);
        return comentsRepository.save(coments);
    }

    @Override
    public void deleteComentario(Long id) {
        comentsRepository.deleteById(id);
    }
}
