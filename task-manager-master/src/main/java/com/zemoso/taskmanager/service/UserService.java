package com.zemoso.taskmanager.service;

import com.zemoso.taskmanager.entity.Task;
import com.zemoso.taskmanager.entity.Users;

import java.util.List;

public interface UserService {
    Users createUser(Users user);
    Users changeRoleToAdmin(Users user);
    List<Users> findAll();
    void deleteUser(String username);
    List<Task> findAllTasks(String username);
    Users getUserByUsername(String username);

}
