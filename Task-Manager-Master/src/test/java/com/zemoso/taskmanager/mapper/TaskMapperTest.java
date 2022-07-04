package com.zemoso.taskmanager.mapper;

import com.zemoso.taskmanager.dto.TaskDTO;
import com.zemoso.taskmanager.entity.Task;
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
class TaskMapperTest {
    @Autowired
    TaskMapper taskMapper;

    @Test
    void taskToTaskDTO() {
        Users user = new Users(1,"saketh","Saketh","saketh@gmail.com","89373633","test123",(short)1,null,null);
        Task newTask =new Task(1,"Task", "CompleteTask","06-07-2022",false,"ganni",user);
        TaskDTO taskDTO = taskMapper.taskToTaskDTO(newTask);

        assertEquals(newTask.getId(), taskDTO.getId());
        assertEquals(newTask.getName(), taskDTO.getName());
        assertEquals(newTask.getDescription(), taskDTO.getDescription());
        assertEquals(newTask.getDate(), taskDTO.getDate());
        assertEquals(newTask.getId(), taskDTO.getId());
        assertEquals(newTask.isCompleted(), taskDTO.isCompleted());
        assertEquals(newTask.getCreatorName(), taskDTO.getCreatorName());
        assertEquals(newTask.getOwner().getUsername(), taskDTO.getOwner());
    }

    @Test
    void taskListToTaskDTOList() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        List<TaskDTO> taskDTOList = taskMapper.taskListToTaskDTOList(taskList);
        assertEquals(taskDTOList.size(),taskList.size());

    }

    @Test
    void taskDTOToTask() {
        TaskDTO taskDTO =new TaskDTO(1,"Task", "CompleteTask","06-07-2022",false,"ganni","saketh");
        Task newTask = taskMapper.taskDTOToTask(taskDTO);
        assertEquals(newTask.getId(), taskDTO.getId());
        assertEquals(newTask.getName(), taskDTO.getName());
        assertEquals(newTask.getDescription(), taskDTO.getDescription());
        assertEquals(newTask.getDate(), taskDTO.getDate());
        assertEquals(newTask.getId(), taskDTO.getId());
        assertEquals(newTask.isCompleted(), taskDTO.isCompleted());
        assertEquals(newTask.getCreatorName(), taskDTO.getCreatorName());
        assertEquals(null,newTask.getOwner());
    }

    @Test
    void map() {
        Users user1 = new Users(1,"saketh","Saketh","saketh@gmail.com","89373633","test123",(short)1,null,null);
        String name= taskMapper.map(user1);
        assertEquals("saketh",name);
    }
}