package com.zemoso.taskmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
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
