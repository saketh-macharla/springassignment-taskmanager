package com.zemoso.taskmanager.controller;

import com.zemoso.taskmanager.mapper.UsersMapper;
import com.zemoso.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UsersMapper usersMapper;

    @GetMapping("/users")
    public String listUsers(Model theModel, SecurityContextHolderAwareRequestWrapper request) {
        boolean isAdminSigned = request.isUserInRole("ROLE_ADMIN");

        theModel.addAttribute("users", usersMapper.usersListToUsersDTOList(userService.findAll()));
        theModel.addAttribute("isAdminSigned", isAdminSigned);
        return "users";
    }

    @GetMapping("user/delete/{username}")
    public String deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return "redirect:/users";
    }
}
