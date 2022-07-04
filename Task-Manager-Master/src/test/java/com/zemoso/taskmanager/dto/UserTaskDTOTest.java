package com.zemoso.taskmanager.dto;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserTaskDTOTest {

    @Test
    void getUserName() {
        UserTaskDTO userTaskDTO = new UserTaskDTO("Saketh",null,null, true);
        String expected ="Saketh";
        assertEquals(userTaskDTO.getUserName(), expected);
    }

    @Test
    void getTaskDTOListInProgress() {

        List<TaskDTO> taskDTOListInProgress= new ArrayList<>();
        taskDTOListInProgress.add(new TaskDTO());
        UserTaskDTO userTaskDTO = new UserTaskDTO("saketh",taskDTOListInProgress,null,true);


        assertEquals(userTaskDTO.getTaskDTOListInProgress(), taskDTOListInProgress);
    }

    @Test
    void getTaskDTOListCompleted() {
        List<TaskDTO> taskDTOListCompleted=new ArrayList<>();
        taskDTOListCompleted.add(new TaskDTO());
        UserTaskDTO userTaskDTO = new UserTaskDTO("saketh",null,taskDTOListCompleted,true);


        assertEquals(userTaskDTO.getTaskDTOListCompleted(),taskDTOListCompleted);
    }

    @Test
    void isAdmin() {
        UserTaskDTO userTaskDTO = new UserTaskDTO("Saketh",null,null, true);
        boolean expected =true;
        assertEquals(userTaskDTO.isAdmin(), expected);
    }

    @Test
    void setUserName() {
        UserTaskDTO userTaskDTO = new UserTaskDTO();
        userTaskDTO.setUserName("Saketh");
        String expected ="Saketh";
        assertEquals(userTaskDTO.getUserName(), expected);
    }

    @Test
    void setTaskDTOListInProgress() {
        UserTaskDTO userTaskDTO = new UserTaskDTO();
        List<TaskDTO> taskDTOListInProgress= new ArrayList<>();
        taskDTOListInProgress.add(new TaskDTO());
        userTaskDTO.setTaskDTOListInProgress(taskDTOListInProgress);


        assertEquals(userTaskDTO.getTaskDTOListInProgress(), taskDTOListInProgress);
    }

    @Test
    void setTaskDTOListCompleted() {
        UserTaskDTO userTaskDTO = new UserTaskDTO();
        List<TaskDTO> taskDTOListCompleted=new ArrayList<>();
        taskDTOListCompleted.add(new TaskDTO());
        userTaskDTO.setTaskDTOListCompleted(taskDTOListCompleted);


        assertEquals(userTaskDTO.getTaskDTOListCompleted(),taskDTOListCompleted);
    }

    @Test
    void setAdmin() {
        UserTaskDTO userTaskDTO = new UserTaskDTO();
        userTaskDTO.setAdmin(false);
        boolean expected =false;
        assertEquals(userTaskDTO.isAdmin(), expected);
    }
}