package com.zemoso.taskmanager.mapper;

import com.zemoso.taskmanager.dto.UsersDTO;
import com.zemoso.taskmanager.entity.Users;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {TasksMapper.class, RolesMapper.class})
public interface UsersMapper {
    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    UsersDTO usersToUsersDTO(Users user);

    List<UsersDTO> usersListToUsersDTOList(List<Users> users);

    Users usersDTOToUsers(UsersDTO usersDTO);
    
}
