package com.zemoso.taskmanager.service;

import com.zemoso.taskmanager.entity.Roles;
import com.zemoso.taskmanager.entity.Task;
import com.zemoso.taskmanager.entity.Users;
import com.zemoso.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    void createUser() {
        List<Task> expectedTasks = Arrays.asList(
                new Task(),
                new Task()
        );
        List<Roles> expectedRoles =new ArrayList<>();
        expectedRoles.add(new Roles("Role_Employee"));
        Users users = new Users(1,"saketh","Saketh","saketh@gmail.com","89373633","test123",(short)1,expectedTasks,expectedRoles);
        when(userRepository.save(users)).thenReturn(users);
        userService.createUser(users);
        verify(userRepository,times(1)).save(users);
    }

    @Test
    void findAll() {
        List<Users> expectedUsers = Arrays.asList(
                new Users(),
                new Users()
        );
        when(userRepository.findAll()).thenReturn(expectedUsers);
        assertEquals(2,userService.findAll().size());
    }

    @Test
    void deleteUser() {
        Users user = new Users(1,"saketh","Saketh","saketh@gmail.com","89373633","test123",(short)1,null,null);
        when(userRepository.findById("saketh")).thenReturn(Optional.of(user));
        userService.deleteUser(user.getUsername());
        verify(userRepository,times(1)).deleteById("saketh");
    }

    @Test
    void findAllTasks() {
        List<Task> expectedTasks = Arrays.asList(
                new Task(),
                new Task()
        );
        List<Roles> expectedRoles =new  ArrayList<>();
        expectedRoles.add(new Roles("Role_Employee"));
        Users users = new Users(1,"saketh","Saketh","saketh@gmail.com","89373633","test123",(short)1,expectedTasks,expectedRoles);
        when(userRepository.findById("saketh")).thenReturn(Optional.of(users));
        assertEquals(2,userService.findAllTasks("saketh").size());

        Optional<Users> nullUser = Optional.ofNullable(null);
        when(userRepository.findById("saketh")).thenReturn(nullUser);
        assertEquals(null, userService.findAllTasks("saketh"));
    }

    @Test
    void getUserByUsername() {
        List<Task> expectedTasks = Arrays.asList(
                new Task(),
                new Task()
        );
        List<Roles> expectedRoles =new  ArrayList<>();
        expectedRoles.add(new Roles("Role_Employee"));
        Users users = new Users(1,"saketh","Saketh","saketh@gmail.com","89373633","test123",(short)1,expectedTasks,expectedRoles);
        when(userRepository.findById("saketh")).thenReturn(Optional.of(users));
        assertEquals(users, userService.getUserByUsername("saketh"));

        Optional<Users> nullUser = Optional.ofNullable(null);
        when(userRepository.findById("saketh")).thenReturn(nullUser);
        assertEquals(null, userService.getUserByUsername("saketh"));
    }

    @Test
    void getTasksInProgress() {
        List<Task> expectedTasks = Arrays.asList(
                new Task(),
                new Task()
        );
        List<Roles> expectedRoles =new  ArrayList<>();
        expectedRoles.add(new Roles("Role_Employee"));
        Users users = new Users(1,"saketh","Saketh","saketh@gmail.com","89373633","test123",(short)1,expectedTasks,expectedRoles);
        when(userRepository.findById("saketh")).thenReturn(Optional.of(users));
        assertEquals(2,userService.getTasksInProgress(users).size());
    }

    @Test
    void getTasksCompleted() {
        List<Task> expectedTasks = Arrays.asList(
                new Task(),
                new Task()
        );
        List<Roles> expectedRoles =new  ArrayList<>();
        expectedRoles.add(new Roles("Role_Employee"));
        Users users = new Users(1,"saketh","Saketh","saketh@gmail.com","89373633","test123",(short)1,expectedTasks,expectedRoles);
        when(userRepository.findById("saketh")).thenReturn(Optional.of(users));
        assertEquals(0,userService.getTasksCompleted(users).size());
    }

    @Test
    void isAdmin() {
        List<Task> expectedTasks = Arrays.asList(
                new Task(),
                new Task()
        );
        List<Roles> expectedRoles =new  ArrayList<>();
        expectedRoles.add(new Roles("Role_Employee"));
        Users users = new Users(1,"saketh","Saketh","saketh@gmail.com","89373633","test123",(short)1,expectedTasks,expectedRoles);
        when(userRepository.findById("saketh")).thenReturn(Optional.of(users));
        assertEquals(false,userService.isAdmin(users));
    }
}