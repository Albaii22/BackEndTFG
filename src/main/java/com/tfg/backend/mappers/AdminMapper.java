package com.tfg.backend.mappers;

import com.tfg.backend.entities.Admin;
import com.tfg.backend.DTO.AdminDTO;

public class AdminMapper {

    public static AdminDTO toDto(Admin admin) {
        return AdminDTO.builder()
                ._id(admin.getId().toString())
                .username(admin.getUsername())
                .privilege_level(admin.getPrivilegeLevel())
                .build();
    }

    public static Admin toEntity(AdminDTO adminDto) {
        return Admin.builder()
                .id(adminDto.get_id() != null ? Long.parseLong(adminDto.get_id()) : null)
                .username(adminDto.getUsername())
                .privilegeLevel(adminDto.getPrivilege_level())
                .build();
    }
}
