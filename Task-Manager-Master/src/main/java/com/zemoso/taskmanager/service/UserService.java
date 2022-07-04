package com.zemoso.taskmanager.service;

import com.zemoso.taskmanager.entity.Task;
import com.zemoso.taskmanager.entity.Users;

import java.util.List;

public interface UserService {
    Users createUser(Users user);

    List<Users> findAll();
    void deleteUser(String username);
    List<Task> findAllTasks(String username);
    Users getUserByUsername(String username);

    List<Task> getTasksInProgress(Users user);

    List<Task> getTasksCompleted(Users user);

    boolean isAdmin(Users user);

}
