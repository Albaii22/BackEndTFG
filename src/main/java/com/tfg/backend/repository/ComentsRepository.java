package com.tfg.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.backend.entities.Comments;

@Repository
public interface ComentsRepository extends JpaRepository<Comments, Long> {

}
