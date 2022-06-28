package com.zemoso.taskmanager.controller;

import com.zemoso.taskmanager.dto.UsersDTO;
import com.zemoso.taskmanager.entity.Roles;
import com.zemoso.taskmanager.entity.Users;
import com.zemoso.taskmanager.mapper.UsersMapper;
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
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UsersMapper usersMapper;

    @RequestMapping("/login")
    public String showLogin(){
        return "forms/login";
    }


    @RequestMapping("/signup")
    public String showSignUp(Model theModel){
        UsersDTO usersDTO = new UsersDTO();
        theModel.addAttribute("user",usersDTO);

        return "forms/signup";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") UsersDTO usersDTO){
        usersDTO.setEnabled((short) 1);
        String encoded=bCryptPasswordEncoder.encode(usersDTO.getPassword());
        usersDTO.setPassword(encoded);

        Users user = usersMapper.usersDTOToUsers(usersDTO);
        Roles role=new Roles("ROLE_EMPLOYEE");
        user.addRole(role);
        userService.createUser(user);

        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String displayProfile(Model theModel, Principal principal){
        String username=principal.getName();
        System.out.println(username);
     UsersDTO user= usersMapper.usersToUsersDTO(userService.getUserByUsername(username));
     theModel.addAttribute("userprofile",user);
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
