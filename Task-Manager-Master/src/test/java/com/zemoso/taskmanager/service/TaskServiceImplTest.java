package com.zemoso.taskmanager.service;

import com.zemoso.taskmanager.entity.Task;
import com.zemoso.taskmanager.entity.Users;
import com.zemoso.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TaskServiceImplTest {

    @MockBean
    TaskRepository taskRepository;

    @Autowired
    TaskService taskService;

    @Test
    void createTask() {
        Users user = new Users(1,"saketh","Saketh","saketh@gmail.com","89373633","test123",(short)1,null,null);

        Task newTask =new Task(1,"Task", "CompleteTask","06-07-2022",false,"ganni",user);
        when(taskRepository.save(newTask)).thenReturn(newTask);
        taskService.createTask(newTask);
        verify(taskRepository,times(1)).save(newTask);
    }

    @Test
    void updateTask() {
        Task newTask =new Task(1,"Task", "CompleteTask","06-07-2022",false,"ganni",null);
        when(taskRepository.save(newTask)).thenReturn(newTask);
        newTask.setName("SpringTask");
        taskService.updateTask(newTask);
        verify(taskRepository,times(1)).save(newTask);
    }

    @Test
    void deleteTask() {
        Task newTask =new Task(1,"Task", "CompleteTask","06-07-2022",false,null,null);
        taskService.deleteTask(1);
        verify(taskRepository,times(1)).deleteById(1);
    }

    @Test
    void markDone() {
        Task newTask =new Task(1,"Task", "CompleteTask","06-07-2022",false,null,null);
        when(taskRepository.findById(1)).thenReturn(Optional.of(newTask));
        taskService.markDone(1);
        verify(taskRepository,times(1)).save(newTask);
    }

    @Test
    void markUnDone() {
        Task newTask =new Task(1,"Task", "CompleteTask","06-07-2022",true,null,null);
        when(taskRepository.findById(1)).thenReturn(Optional.of(newTask));
        taskService.markUnDone(1);
        verify(taskRepository,times(1)).save(newTask);
    }

    @Test
    void getTaskById() {
        Task newTask =new Task(1,"Task", "CompleteTask","06-07-2022",true,null,null);
        when(taskRepository.findById(1)).thenReturn(Optional.of(newTask));
        assertEquals(newTask,taskService.getTaskById(1));

        Optional<Task> nullTask = Optional.ofNullable(null);
        when(taskRepository.findById(1)).thenReturn(nullTask);
        assertEquals(null, taskService.getTaskById(1));
    }



    @Test
    void findFreeTasks() {
        List<Task> taskList = new ArrayList<>();
        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();
        task1.setCompleted(false);
        task2.setCompleted(false);
        task3.setCompleted(false);
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        when(taskRepository.findAll()).thenReturn(taskList);
        assertEquals(3,taskService.findFreeTasks().size());
    }

    @Test
    void unassignTask() {
        Users users = new Users(1,"saketh","Saketh","saketh@gmail.com","89373633","test123",(short)1,null,null);
        Task task = new Task();
        task.setOwner(users);
        when(taskRepository.save(task)).thenReturn(task);
        taskService.unassignTask(task);
        assertEquals(null, task.getOwner());
    }

    @Test
    void assignTask() {
        Users users = new Users(1,"saketh","Saketh","saketh@gmail.com","89373633","test123",(short)1,null,null);
        Task task = new Task();
        when(taskRepository.save(task)).thenReturn(task);
        taskService.assignTask(task, users);
        assertEquals(users, task.getOwner());
    }

    @Test
    void findAll() {
        List<Task> taskList = new ArrayList<>();
        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        when(taskRepository.findAll()).thenReturn(taskList);
        assertEquals(3,taskService.findAll().size());
    }
}