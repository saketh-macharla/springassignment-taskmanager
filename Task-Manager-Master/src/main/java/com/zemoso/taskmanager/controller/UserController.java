package com.zemoso.taskmanager.controller;

import com.zemoso.taskmanager.dto.UserTaskDTO;
import com.zemoso.taskmanager.entity.Users;
import com.zemoso.taskmanager.mapper.TaskMapper;
import com.zemoso.taskmanager.mapper.UserMapper;
import com.zemoso.taskmanager.service.TaskService;
import com.zemoso.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/users")
    public String listUsers(Model theModel, SecurityContextHolderAwareRequestWrapper request) {
        boolean isAdminSigned = request.isUserInRole("ROLE_ADMIN");

        List<UserTaskDTO> userTaskDTOList = new ArrayList<>();
        List<Users> usersList = userService.findAll();
        for (Users user: usersList) {
            UserTaskDTO userTaskDTO = new UserTaskDTO();
            userTaskDTO.setUserName(user.getUsername());
            userTaskDTO.setTaskDTOListInProgress(taskMapper.taskListToTaskDTOList(userService.getTasksInProgress(user)));
            userTaskDTO.setTaskDTOListCompleted(taskMapper.taskListToTaskDTOList(userService.getTasksCompleted(user)));
            userTaskDTO.setAdmin(userService.isAdmin(user));
            userTaskDTOList.add(userTaskDTO);

        }
        theModel.addAttribute("users", userTaskDTOList);
        theModel.addAttribute("isAdminSigned", isAdminSigned);
        return "users";
    }

    @GetMapping("user/delete/{username}")
    public String deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return "redirect:/users";
    }
}
