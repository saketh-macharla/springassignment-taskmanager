package com.zemoso.taskmanager.controller;

import com.zemoso.taskmanager.entity.Task;
import com.zemoso.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TaskControllerTest {

    @Mock
    TaskService taskServiceMock;

    @InjectMocks
    TaskController taskController;
    private MockMvc mockMvc;

    @org.junit.jupiter.api.BeforeEach
    @WithMockUser(username = "admin",authorities = {"ADMIN"})
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void updateTask_shouldReturnStatusOkAndFilledTaskFormAsViewNameAndTaskAsAttribute() throws Exception {
        int id = 1;

        when(taskServiceMock.getTaskById(id)).thenReturn(new Task());

        mockMvc.perform(get("/task/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/task-edit"))
                .andExpect(model().attribute("task", instanceOf(Task.class)));
    }

    @Test
    public void deleteTask_shouldInvokeDeleteTaskMethodAndRedirectToHome() throws Exception {
        int id = 1;


        mockMvc.perform(get("/task/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));

        verify(taskServiceMock, times(1)).deleteTask(id);
    }

}
