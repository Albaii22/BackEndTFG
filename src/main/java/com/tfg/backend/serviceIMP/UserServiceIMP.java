package com.tfg.backend.serviceIMP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.backend.entities.User;
import com.tfg.backend.repository.UserRepository;
import com.tfg.backend.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceIMP implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsuarios() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUsuarioById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUsuario(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUsuario(Long id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public void deleteUsuario(Long id) {
        userRepository.deleteById(id);
    }
}
