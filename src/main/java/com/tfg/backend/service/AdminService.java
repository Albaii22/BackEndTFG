package com.tfg.backend.service;

import com.tfg.backend.DTO.AdminDTO;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<AdminDTO> getAllAdmins();
    Optional<AdminDTO> getAdminById(Long id);
    AdminDTO createAdmin(AdminDTO adminDTO);
    AdminDTO updateAdmin(Long id, AdminDTO adminDTO);
    void deleteAdmin(Long id);
}
