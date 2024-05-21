package com.tfg.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.backend.entities.Publications;
import com.tfg.backend.entities.User;

@Repository
public interface PublicationsRepository extends JpaRepository<Publications, Long>{
    List<Publications> findByUser(User user);
}
