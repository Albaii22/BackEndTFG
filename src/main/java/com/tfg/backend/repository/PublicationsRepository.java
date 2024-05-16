package com.tfg.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.backend.entities.Publications;

@Repository
public interface PublicationsRepository extends JpaRepository<Publications, Long>{

}
