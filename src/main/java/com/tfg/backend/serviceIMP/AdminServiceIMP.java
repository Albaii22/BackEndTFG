package com.tfg.backend.serviceIMP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.tfg.backend.DTO.AdminDTO;
import com.tfg.backend.entities.Admin;
import com.tfg.backend.repository.AdminRepository;
import com.tfg.backend.service.AdminService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
// Service implementation for managing Admins
public class AdminServiceIMP implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceIMP.class);

    @Autowired
    private AdminRepository adminRepository; // Repository for admin data

    // Retrieves all admins
    @Override
    public List<AdminDTO> getAllAdmins() {
        try {
            List<Admin> admins = adminRepository.findAll();
            return admins.stream()
                         .map(this::convertToDTO)
                         .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("Failed to fetch all admins", e);
            return Collections.emptyList();
        }
    }

    // Retrieves an admin by ID
    @Override
    public Optional<AdminDTO> getAdminById(Long id) {
        try {
            return adminRepository.findById(id)
                                  .map(this::convertToDTO);
        } catch (DataAccessException e) {
            logger.error("Failed to fetch admin by ID: {}", id, e);
            return Optional.empty();
        }
    }

    // Creates a new admin
    @Override
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        try {
            Admin admin = convertToEntity(adminDTO);
            Admin createdAdmin = adminRepository.save(admin);
            return convertToDTO(createdAdmin);
        } catch (DataAccessException e) {
            logger.error("Failed to create admin with username: {}", adminDTO.getUsername(), e);
            return null;
        }
    }

    // Updates an existing admin
    @Override
    public AdminDTO updateAdmin(Long id, AdminDTO adminDTO) {
        return adminRepository.findById(id).map(existingAdmin -> {
            existingAdmin.setUsername(adminDTO.getUsername());
            return convertToDTO(adminRepository.save(existingAdmin));
        }).orElseGet(() -> {
            logger.error("Failed to update admin with ID: {}", id);
            return null;
        });
    }

    // Deletes an admin by ID
    @Override
    public void deleteAdmin(Long id) {
        try {
            adminRepository.deleteById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to delete admin with ID: {}", id, e);
        }
    }

    // Converts an Admin entity to AdminDTO
    private AdminDTO convertToDTO(Admin admin) {
        return AdminDTO.builder()
                       .username(admin.getUsername())
                       .build();
    }

    // Converts an AdminDTO to Admin entity
    private Admin convertToEntity(AdminDTO adminDTO) {
        return Admin.builder()
                    .username(adminDTO.getUsername())
                    .build();
    }
}
