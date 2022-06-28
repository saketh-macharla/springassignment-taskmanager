package com.zemoso.taskmanager.controller;

import com.zemoso.taskmanager.dto.TasksDTO;
import com.zemoso.taskmanager.dto.UsersDTO;
import com.zemoso.taskmanager.entity.Task;
import com.zemoso.taskmanager.entity.Users;
import com.zemoso.taskmanager.mapper.TasksMapper;
import com.zemoso.taskmanager.mapper.UsersMapper;
import com.zemoso.taskmanager.service.TaskService;
import com.zemoso.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private TasksMapper tasksMapper;

    @Autowired
    private UsersMapper usersMapper;

    @RequestMapping("home")
    public String getAllTasks(Model theModel, Principal principal, SecurityContextHolderAwareRequestWrapper request){
        String username= principal.getName();
        List<TasksDTO> tasks = tasksMapper.tasksListToTasksDTOList(userService.findAllTasks(username));
        boolean isAdminSigned = request.isUserInRole("ROLE_ADMIN");

        System.out.println(tasks);
        theModel.addAttribute("username",username);
        theModel.addAttribute("allTasks",tasks);
        theModel.addAttribute("isAdminSigned", isAdminSigned);
        return "home";
    }

    @GetMapping("/tasks")
    public String listTasks(Model model, Principal principal, SecurityContextHolderAwareRequestWrapper request) {
        String username= principal.getName();
        UsersDTO signedUser = usersMapper.usersToUsersDTO(userService.getUserByUsername(username));
        boolean isAdminSigned = request.isUserInRole("ROLE_ADMIN");

        model.addAttribute("allTasks", tasksMapper.tasksListToTasksDTOList(taskService.findAll()));
        model.addAttribute("isAdminSigned", isAdminSigned);
        return "tasks";
    }

    @GetMapping("task/mark/{taskId}")
    public String markTaskAsDone(@PathVariable("taskId") int taskId){
        taskService.markDone(taskId);
        return "redirect:/home";
    }

    @GetMapping("task/unmark/{taskId}")
    public String markTaskAsUnDone(@PathVariable("taskId") int taskId){
        taskService.markUnDone(taskId);
        return "redirect:/home";
    }

    @GetMapping("task/create")
    public String showTaskForm(Model theModel, Principal principal, SecurityContextHolderAwareRequestWrapper request){
        String userName = principal.getName();
        System.out.println("\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>  principal.getName(): "+ userName);
        UsersDTO user = usersMapper.usersToUsersDTO(userService.getUserByUsername(userName));

        TasksDTO newTask = new TasksDTO();
        newTask.setCreatorName(user.getUsername());
        newTask.setOwner(user);
        theModel.addAttribute("task", newTask);
        return "forms/task-new";
    }

    @PostMapping("task/create")
    public String createTaskFormSuccess(@Valid TasksDTO task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "forms/task-new";
        }
        taskService.createTask(tasksMapper.tasksDTOToTasks(task));

        return "redirect:/home";
    }

    @GetMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
        return "redirect:/home";
    }

    @GetMapping("/task/edit/{id}")
    public String showFilledTaskForm(@PathVariable int id, Model model) {
        model.addAttribute("task", tasksMapper.tasksToTasksDTO(taskService.getTaskById(id)));
        return "forms/task-edit";
    }

    @PostMapping("/task/edit/{id}")
    public String updateTask(@Valid TasksDTO task, BindingResult bindingResult, @PathVariable int id, Model model) {
        if (bindingResult.hasErrors()) {
            return "forms/task-edit";
        }
        taskService.updateTask(id, tasksMapper.tasksDTOToTasks(task));
        return "redirect:/home";
    }
}
