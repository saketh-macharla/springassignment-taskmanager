package com.zemoso.taskmanager.service;

import com.zemoso.taskmanager.repository.TaskRepository;
import com.zemoso.taskmanager.entity.Task;
import com.zemoso.taskmanager.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    TaskRepository taskRepository;


    @Override
    public void createTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void updateTask(int id, Task task) {
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void markDone(int id) {
         Optional<Task> task= taskRepository.findById(id);
         Task tempTask = null;
         if(task.isPresent()){
             tempTask=task.get();
         }
         tempTask.setCompleted(true);
         taskRepository.save(tempTask);
    }

    @Override
    public void markUnDone(int id) {
        Optional<Task> task= taskRepository.findById(id);
        Task tempTask = null;
        if(task.isPresent()){
            tempTask=task.get();
        }
        tempTask.setCompleted(false);
        taskRepository.save(tempTask);
    }

    @Override
    public Task getTaskById(int id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<Task> findFreeTasks(){
        return taskRepository.findAll()
                .stream()
                .filter(task -> task.getOwner() == null && !task.isCompleted())
                .collect(Collectors.toList());
    }

    @Override
    public void unassignTask(Task selectedTask) {
            selectedTask.setOwner(null);
            taskRepository.save(selectedTask);
    }

    @Override
    public void assignTask(Task selectedTask, Users selectedUser) {
        selectedTask.setOwner(selectedUser);
        taskRepository.save(selectedTask);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

}
