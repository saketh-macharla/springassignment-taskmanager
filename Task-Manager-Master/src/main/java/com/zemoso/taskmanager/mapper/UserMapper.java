package com.zemoso.taskmanager.mapper;

import com.zemoso.taskmanager.dto.UserDTO;
import com.zemoso.taskmanager.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(Users user);

    List<UserDTO> userListToUserDTOList(List<Users> userList);

    @Mapping(target = "tasksOwned", expression = "java(null)")
    Users userDTOToUser(UserDTO userDTO);
}
