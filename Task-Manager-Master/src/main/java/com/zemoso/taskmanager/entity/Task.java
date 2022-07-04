package com.zemoso.taskmanager.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name of the task is needed. Please write name of this task.")
    private String name;
    @NotEmpty(message = "Description of the task can not be empty. Please write short description of this task.")
    @Size(max = 1200, message = "Description of the task can be up to 1200 characters long. This description is longer. Please try to write shorter description.")
    private String description;
    @NotNull(message = "Date provided for completing this task is needed. Please choose the date.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;
    @Column(name="is_completed")
    private boolean completed;
    @Column(name="creator_name")
    private String creatorName;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="owner_username")
    private Users owner;

    public String toString(){
        return "["+id+","+name+","+description+","+date+","+completed+","+creatorName+"]";
    }
}
