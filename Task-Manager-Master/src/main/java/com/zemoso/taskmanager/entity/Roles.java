package com.zemoso.taskmanager.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Roles {
    @Column(name="authority")
    @Id
    private String authority;

    @ManyToMany(mappedBy="roles")
    private List<Users> users;
    public Roles(String authority){
        this.authority=authority;
    }



}
