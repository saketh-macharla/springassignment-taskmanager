package com.zemoso.taskmanager.mapper;

import com.zemoso.taskmanager.dto.UserDTO;
import com.zemoso.taskmanager.entity.Roles;
import com.zemoso.taskmanager.entity.Task;
import com.zemoso.taskmanager.entity.Users;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void userToUserDTO() {
        List<Task> expectedTasks = Arrays.asList(
                new Task(),
                new Task()
        );
        List<Roles> expectedRoles =new ArrayList<>();
        expectedRoles.add(new Roles("Role_Employee"));
        Users user = new Users(1,"saketh","Saketh","saketh@gmail.com","89373633","test123",(short)1,expectedTasks,expectedRoles);
        UserDTO userDTO = userMapper.userToUserDTO(user);
        assertEquals(user.getUserId(),userDTO.getUserId());
        assertEquals(user.getUsername(),userDTO.getUsername());
        assertEquals(user.getName(),userDTO.getName());
        assertEquals(user.getEmail(),userDTO.getEmail());
        assertEquals(user.getPassword(),userDTO.getPassword());
        assertEquals(user.getPhone(), userDTO.getPhone());
        assertEquals(user.getEnabled(),userDTO.getEnabled());
    }

    @Test
    void userListToUserDTOList() {
        List<Users> usersList = new ArrayList<>();
        Users user1 = new Users(1,"saketh","Saketh","saketh@gmail.com","89373633","test123",(short)1,null,null);
        Users user2 = new Users(1,"ganni","Ganesh","ganni@gmail.com","89373333","test123",(short)1,null,null);
        Users user3 = new Users(1,"dende","Dende","dende@gmail.com","89373933","test123",(short)1,null,null);
        usersList.add(user1);
        usersList.add(user2);
        usersList.add(user3);
        List<UserDTO> userDTOList = userMapper.userListToUserDTOList(usersList);
        assertEquals(userDTOList.size(),usersList.size());

    }

    @Test
    void userDTOToUser() {
        UserDTO userDTO = new UserDTO(1,"saketh","Saketh","saketh@gmail.com","89373633","test123",(short)1);
        Users user = userMapper.userDTOToUser(userDTO);
        assertEquals(user.getUserId(),userDTO.getUserId());
        assertEquals(user.getUsername(),userDTO.getUsername());
        assertEquals(user.getName(),userDTO.getName());
        assertEquals(user.getEmail(),userDTO.getEmail());
        assertEquals(user.getPassword(),userDTO.getPassword());
        assertEquals(user.getPhone(), userDTO.getPhone());
        assertEquals(user.getEnabled(),userDTO.getEnabled());
    }


}