package com.zemoso.taskmanager.controller;

import com.zemoso.taskmanager.dto.TaskDTO;
import com.zemoso.taskmanager.entity.Task;
import com.zemoso.taskmanager.entity.Users;
import com.zemoso.taskmanager.mapper.TaskMapper;
import com.zemoso.taskmanager.mapper.UserMapper;
import com.zemoso.taskmanager.service.TaskService;
import com.zemoso.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class TaskController {
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("home")
    public String getAllTasks(Model theModel, Principal principal, SecurityContextHolderAwareRequestWrapper request){
        String username= principal.getName();
        List<TaskDTO> tasks =taskMapper.taskListToTaskDTOList(userService.findAllTasks(username));
        boolean isAdminSigned = request.isUserInRole("ROLE_ADMIN");

        System.out.println(tasks);
        theModel.addAttribute("username",username);
        theModel.addAttribute("allTasks",tasks);
        theModel.addAttribute("isAdminSigned", isAdminSigned);
        return "home";
    }

    @GetMapping("/tasks")
    public String listTasks(Model model,SecurityContextHolderAwareRequestWrapper request) {

        boolean isAdminSigned = request.isUserInRole("ROLE_ADMIN");

        model.addAttribute("allTasks", taskMapper.taskListToTaskDTOList(taskService.findAll()));
        model.addAttribute("isAdminSigned", isAdminSigned);
        model.addAttribute("isHome","False");
        return "tasks";
    }

    @GetMapping("task/mark/{taskId}")
    public String markTaskAsDone(@PathVariable("taskId") int taskId, @RequestParam String isHome){
        taskService.markDone(taskId);
        if(isHome.equalsIgnoreCase("true"))
            return "redirect:/home";
        else{
            return "redirect:/tasks";
        }
    }

    @GetMapping("task/unmark/{taskId}")
    public String markTaskAsUnDone(@PathVariable("taskId") int taskId, @RequestParam String isHome){
        taskService.markUnDone(taskId);

        if(isHome.equalsIgnoreCase("true"))
            return "redirect:/home";
        else{
            return "redirect:/tasks";
        }
    }

    @GetMapping("task/create")
    public String showTaskForm(Model theModel){
        TaskDTO newTask = new TaskDTO();
        theModel.addAttribute("task", newTask);
        return "forms/task-new";
    }

    @PostMapping("task/create")
    public String createTaskFormSuccess(@Valid @ModelAttribute("task") TaskDTO taskDTO, BindingResult bindingResult, Principal loggedUser) {
        if (bindingResult.hasErrors()) {
            return "forms/task-new";
        }
        Users user = userService.getUserByUsername(loggedUser.getName());
        taskDTO.setCreatorName(user.getUsername());
        Task task = taskMapper.taskDTOToTask(taskDTO);
        task.setOwner(user);
        taskService.createTask(task);
        return "redirect:/home";
    }

    @GetMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable int id, @RequestParam String isHome) {
        taskService.deleteTask(id);
        if(isHome.equalsIgnoreCase("true"))
            return "redirect:/home";
        else{
            return "redirect:/tasks";
        }
    }

    @GetMapping("/task/edit/{id}")
    public String showFilledTaskForm(@PathVariable int id, Model model, @RequestParam String isHome) {
        model.addAttribute("task", taskMapper.taskToTaskDTO(taskService.getTaskById(id)));
        model.addAttribute("isHome",isHome);
        return "forms/task-edit";
    }

    @PostMapping("/task/edit")
    public String updateTask(@Valid @ModelAttribute("task") TaskDTO taskDTO, BindingResult bindingResult, @RequestParam String isHome) {
        if (bindingResult.hasErrors()) {
            return "forms/task-edit";
        }
        Users user = userService.getUserByUsername(taskDTO.getOwner());
        Task task = taskMapper.taskDTOToTask(taskDTO);
        task.setOwner(user);
        taskService.updateTask(task);
        if(isHome.equalsIgnoreCase("true"))
             return "redirect:/home";
        else{
            return  "redirect:/tasks";
        }
    }
}
