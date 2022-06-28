package com.zemoso.taskmanager.mapper;

import com.zemoso.taskmanager.dto.RolesDTO;
import com.zemoso.taskmanager.entity.Roles;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RolesMapper {

    RolesMapper INSTANCE = Mappers.getMapper(RolesMapper.class);

    RolesDTO rolesToRolesDTO(Roles roles);
}
