package com.zemoso.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {

    private int userId;

    private String username;

    @NotEmpty(message = "Name can not be empty. Please enter your name")
    private String name;

    @Email(message = "It looks like it is not an email. Please enter your email.")
    @NotEmpty(message = "Email can not be empty. Please enter your email.")
    private String email;


    private String phone;


    private String password;


    private short enabled;
}
