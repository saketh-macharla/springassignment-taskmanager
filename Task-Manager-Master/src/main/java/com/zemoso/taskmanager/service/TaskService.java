package com.zemoso.taskmanager.service;

import com.zemoso.taskmanager.entity.Task;
import com.zemoso.taskmanager.entity.Users;

import java.util.List;

public interface TaskService {
    void createTask(Task task);

    void updateTask(Task task);

    void deleteTask(int  id);
    void markDone(int id);
    void markUnDone(int id);

    Task getTaskById(int id);

     List<Task> findFreeTasks();

    void unassignTask(Task selectedTask);

    void assignTask(Task selectedTask, Users selectedUser);

    List<Task> findAll();
}
