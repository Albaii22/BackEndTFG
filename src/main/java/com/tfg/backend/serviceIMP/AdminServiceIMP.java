package com.tfg.backend.serviceIMP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.backend.DTO.AdminDTO;
import com.tfg.backend.entities.Admin;
import com.tfg.backend.mappers.AdminMapper;
import com.tfg.backend.repository.AdminRepository;
import com.tfg.backend.service.AdminService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceIMP implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<AdminDTO> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream()
                     .map(AdminMapper.INSTANCE::adminToAdminDTO)
                     .collect(Collectors.toList());
    }

    @Override
    public Optional<AdminDTO> getAdminById(Long id) {
        return adminRepository.findById(id)
                              .map(AdminMapper.INSTANCE::adminToAdminDTO);
    }

    @Override
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        Admin admin = AdminMapper.INSTANCE.adminDTOToAdmin(adminDTO);
        Admin createdAdmin = adminRepository.save(admin);
        return AdminMapper.INSTANCE.adminToAdminDTO(createdAdmin);
    }

    @Override
    public AdminDTO updateAdmin(Long id, AdminDTO adminDTO) {
        Admin admin = AdminMapper.INSTANCE.adminDTOToAdmin(adminDTO);
        admin.setId(id);
        Admin updatedAdmin = adminRepository.save(admin);
        return AdminMapper.INSTANCE.adminToAdminDTO(updatedAdmin);
    }

    @Override
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}
