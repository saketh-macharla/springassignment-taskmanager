package com.zemoso.taskmanager.mapper;

import com.zemoso.taskmanager.dto.RoleDTO;
import com.zemoso.taskmanager.entity.Roles;
import com.zemoso.taskmanager.entity.Users;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RoleMapperTest {

    @Autowired
    RoleMapper roleMapper;

    @Test
    void roleToRoleDTO() {
        Users user = new Users(1,"saketh","Saketh","saketh@gmail.com","89373633","test123",(short)1,null,null);
        List<Users> usersList = new ArrayList<>();
        usersList.add(user);
        Roles role = new Roles("Role_Employee", usersList);
        RoleDTO roleDTO = roleMapper.roleToRoleDTO(role);

        assertEquals(role.getAuthority(), roleDTO.getAuthority());
    }

    @Test
    void roleDTOtoRole() {
        RoleDTO roleDTO = new RoleDTO("Role_Employee");
        Roles role = roleMapper.roleDTOtoRole(roleDTO);
        assertEquals(roleDTO.getAuthority(),role.getAuthority());
        assertEquals(null,role.getUsers());
    }
}