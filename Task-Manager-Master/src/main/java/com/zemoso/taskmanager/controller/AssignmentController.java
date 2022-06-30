package com.zemoso.taskmanager.controller;

import com.zemoso.taskmanager.dto.UserTaskDTO;
import com.zemoso.taskmanager.entity.Task;
import com.zemoso.taskmanager.entity.Users;
import com.zemoso.taskmanager.mapper.TaskMapper;
import com.zemoso.taskmanager.service.TaskService;
import com.zemoso.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AssignmentController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskMapper taskMapper;

    @GetMapping("/assignment")
    public String AssignmentForm(Model theModel) {

        List<UserTaskDTO> userTaskDTOList = new ArrayList<>();
        List<Users> usersList = userService.findAll();
        for (Users user : usersList) {
            UserTaskDTO userTaskDTO = new UserTaskDTO();
            userTaskDTO.setUserName(user.getUsername());
            userTaskDTO.setTaskDTOListInProgress(taskMapper.taskListToTaskDTOList(userService.getTasksInProgress(user)));
            userTaskDTO.setTaskDTOListCompleted(null);
            userTaskDTO.setAdmin(userService.isAdmin(user));
            userTaskDTOList.add(userTaskDTO);

            theModel.addAttribute("users", userTaskDTOList);
//        theModel.addAttribute("freeTasks", taskService.findFreeTasks());
        }
        return "forms/assignment";
    }

    @GetMapping("/assignment/{username}")
    public String userAssignmentForm(Model theModel, @PathVariable String username){
        String selectedUser = username;
        Users User = userService.getUserByUsername(selectedUser);
//        theModel.addAttribute("users", userService.findAll());
        List<UserTaskDTO> userTaskDTOList = new ArrayList<>();
        List<Users> usersList = userService.findAll();
        for (Users user : usersList) {
            UserTaskDTO userTaskDTO = new UserTaskDTO();
            userTaskDTO.setUserName(user.getUsername());
            userTaskDTO.setTaskDTOListInProgress(taskMapper.taskListToTaskDTOList(userService.getTasksInProgress(user)));
            userTaskDTO.setTaskDTOListCompleted(null);
            userTaskDTO.setAdmin(userService.isAdmin(user));
            userTaskDTOList.add(userTaskDTO);
        }
        theModel.addAttribute("users", userTaskDTOList);
        theModel.addAttribute("freeTasks", taskMapper.taskListToTaskDTOList(taskService.findFreeTasks()));
        theModel.addAttribute("selectedUser",selectedUser);
        theModel.addAttribute("selectedUserTasksInProgress", taskMapper.taskListToTaskDTOList(userService.getTasksInProgress(User)));
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
