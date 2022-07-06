package com.zemoso.taskmanager.service;

import com.zemoso.taskmanager.repository.TaskRepository;
import com.zemoso.taskmanager.entity.Task;
import com.zemoso.taskmanager.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    TaskRepository taskRepository;

    private Logger myLogger = Logger.getLogger(getClass().getName());

    @Override
    public void createTask(Task task) {
        taskRepository.save(task);
        myLogger.info("A new Task is created by: "+ task.getOwner().getUsername());
    }

    @Override
    public void updateTask(Task task) {
        taskRepository.save(task);
        myLogger.info("Updated task details of task with id: "+task.getId());
    }

    @Override
    public void deleteTask(int id) {
        taskRepository.deleteById(id);
        myLogger.info("Deleted task with id: "+ id);
    }

    @Override
    public void markDone(int id) {
         Optional<Task> task= taskRepository.findById(id);
         Task tempTask = null;
         if(task.isPresent()) {
             tempTask = task.get();

             tempTask.setCompleted(true);
             taskRepository.save(tempTask);
             myLogger.info("Task with id: "+ id + " is marked done");
         }
    }

    @Override
    public void markUnDone(int id) {
        Optional<Task> task= taskRepository.findById(id);
        Task tempTask = null;
        if(task.isPresent()) {
            tempTask = task.get();

            tempTask.setCompleted(false);
            taskRepository.save(tempTask);
            myLogger.info("Task with id: "+ id + " is marked undone");
        }
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
       Users user = selectedTask.getOwner();
            selectedTask.setOwner(null);
            taskRepository.save(selectedTask);
        myLogger.info("Unassigned a Task with id: "+selectedTask.getId() +" from  User: "+ user.getUsername());
    }

    @Override
    public void assignTask(Task selectedTask, Users selectedUser) {
        selectedTask.setOwner(selectedUser);
        taskRepository.save(selectedTask);
        myLogger.info("Assigned a Task with id: "+selectedTask.getId() +" to  User: "+ selectedUser.getUsername());
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

}
