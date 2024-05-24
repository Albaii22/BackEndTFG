package com.tfg.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.backend.entities.Admin;

@Repository
// Repository interface for managing Admin entities
public interface AdminRepository extends JpaRepository<Admin, Long> {

}
