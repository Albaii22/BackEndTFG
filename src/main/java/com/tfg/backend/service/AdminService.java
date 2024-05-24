package com.tfg.backend.service;

import com.tfg.backend.DTO.AdminDTO;

import java.util.List;
import java.util.Optional;

// Service interface for managing Admins
public interface AdminService {
    // Retrieves all admins
    List<AdminDTO> getAllAdmins();
    
    // Retrieves an admin by ID
    Optional<AdminDTO> getAdminById(Long id);
    
    // Creates a new admin
    AdminDTO createAdmin(AdminDTO adminDTO);
    
    // Updates an existing admin
    AdminDTO updateAdmin(Long id, AdminDTO adminDTO);
    
    // Deletes an admin by ID
    void deleteAdmin(Long id);
}
