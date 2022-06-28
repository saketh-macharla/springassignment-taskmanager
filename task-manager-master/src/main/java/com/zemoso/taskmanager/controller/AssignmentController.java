package com.zemoso.taskmanager.controller;

import com.zemoso.taskmanager.entity.Task;
import com.zemoso.taskmanager.entity.Users;
import com.zemoso.taskmanager.mapper.TasksMapper;
import com.zemoso.taskmanager.mapper.UsersMapper;
import com.zemoso.taskmanager.service.TaskService;
import com.zemoso.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AssignmentController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private TasksMapper tasksMapper;

    @GetMapping("/assignment")
    public String AssignmentForm(Model theModel){
        theModel.addAttribute("users", usersMapper.usersListToUsersDTOList(userService.findAll()));
        theModel.addAttribute("freeTasks", tasksMapper.tasksListToTasksDTOList(taskService.findFreeTasks()));
        return "forms/assignment";
    }

    @GetMapping("/assignment/{username}")
    public String userAssignmentForm(Model theModel, @PathVariable String username){
        theModel.addAttribute("users", usersMapper.usersListToUsersDTOList(userService.findAll()));
        theModel.addAttribute("freeTasks", tasksMapper.tasksListToTasksDTOList(taskService.findFreeTasks()));
        theModel.addAttribute("selectedUser", usersMapper.usersToUsersDTO(userService.getUserByUsername(username)));
        return "forms/assignment";
    }

    @GetMapping("/assignment/unassign/{username}/{taskId}")
    public String unassignTask( @PathVariable String username, @PathVariable int taskId){
        Task selectedTask = taskService.getTaskById(taskId);
        taskService.unassignTask(selectedTask);
        return "redirect:/assignment/"+username;
    }

    @GetMapping("/assignment/assign/{username}/{taskId}")
    public String assignTask( @PathVariable String username, @PathVariable int taskId){
        Task selectedTask = taskService.getTaskById(taskId);
        Users selectedUser = userService.getUserByUsername(username);
        taskService.assignTask(selectedTask,selectedUser);
        return "redirect:/assignment/"+username;
    }
}
