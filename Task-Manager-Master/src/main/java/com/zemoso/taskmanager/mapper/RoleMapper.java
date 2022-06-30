package com.zemoso.taskmanager.mapper;

import com.zemoso.taskmanager.dto.RoleDTO;
import com.zemoso.taskmanager.entity.Roles;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDTO roleToRoleDTO(Roles role);

    Roles roleDTOtoRole(RoleDTO roleDTO);
}
