package com.zemoso.taskmanager.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginController {
    private MockMvc mockMvc;


    LoginController loginController;

    @org.junit.jupiter.api.BeforeEach
    public void setup(){
         loginController = new LoginController();
        mockMvc= MockMvcBuilders.standaloneSetup(loginController).build();
    }
    @Test
    public void showLoginForm_shouldReturnStatusOkAndLoginFormAsViewName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("forms/login"));
    }
}
