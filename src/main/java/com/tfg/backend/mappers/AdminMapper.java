package com.tfg.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.tfg.backend.DTO.AdminDTO;
import com.tfg.backend.entities.Admin;

@Mapper
public interface AdminMapper {

    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    @Mapping(source = "id", target = "_id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "privilegeLevel", target = "privilege_level")
    AdminDTO adminToAdminDTO(Admin admin);

    @Mapping(source = "_id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "privilege_level", target = "privilegeLevel")
    Admin adminDTOToAdmin(AdminDTO adminDTO);
}
