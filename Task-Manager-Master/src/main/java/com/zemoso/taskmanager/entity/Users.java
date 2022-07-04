package com.zemoso.taskmanager.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Id
    @Column(name="username")
    private String username;

    @NotEmpty(message = "Name can not be empty. Please enter your name")
    @Column(name="name")
    private String name;

    @Email(message = "It looks like it is not an email. Please enter your email.")
    @NotEmpty(message = "Email can not be empty. Please enter your email.")
    @Column(name="email")
    private String email;
    @Column(name="phone")
    private String phone;
    @Column(name="password")
    private String password;

    @Column(name="enabled")
    private short enabled;
    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Task> tasksOwned;
    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name="authorities",
            joinColumns=@JoinColumn(name="username"),
            inverseJoinColumns = @JoinColumn(name="authority")
    )
    private List<Roles> roles;
    public  void addRole(Roles role){
        if(roles==null){
            roles=new ArrayList<>();
        }
        roles.add(role);


    }
    public void addTask(Task task){
        if(task==null){
            tasksOwned=new ArrayList<>();
        }
        tasksOwned.add(task);
    }


    public String toString(){
        return "["+userId+","+username+","+name+","+email+","+phone+","+password+"]";
    }


}
