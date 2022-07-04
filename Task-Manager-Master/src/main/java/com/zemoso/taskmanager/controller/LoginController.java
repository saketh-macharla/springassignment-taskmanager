package com.zemoso.taskmanager.controller;

import com.zemoso.taskmanager.dto.UserDTO;
import com.zemoso.taskmanager.entity.Roles;
import com.zemoso.taskmanager.entity.Users;
import com.zemoso.taskmanager.mapper.UserMapper;
import com.zemoso.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/login")
    public String showLogin(){
        return "forms/login";
    }


    @RequestMapping("/signup")
    public String showSignUp(Model theModel){
        UserDTO userDTO= new UserDTO();
        theModel.addAttribute("user",userDTO);

        return "forms/signup";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") UserDTO userDTO){
        userDTO.setEnabled((short) 1);
        String encoded=bCryptPasswordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encoded);
        Users user = userMapper.userDTOToUser(userDTO);
        Roles role=new Roles("ROLE_EMPLOYEE");
        user.addRole(role);
        user.setUserId(0);
        userService.createUser(user);

        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String displayProfile(Model theModel, Principal principal){
        String username=principal.getName();
        System.out.println(username);
     UserDTO userDTO= userMapper.userToUserDTO(userService.getUserByUsername(username));
     theModel.addAttribute("userprofile",userDTO);
        return "profile";
    }

    @RequestMapping(value="/logout", method= RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

}
