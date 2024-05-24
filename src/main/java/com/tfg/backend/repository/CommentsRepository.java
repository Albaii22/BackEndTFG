package com.tfg.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.backend.entities.Comments;

@Repository
// Repository interface for managing Comments entities
public interface CommentsRepository extends JpaRepository<Comments, Long> {

}
