package com.zemoso.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TasksDTO {

    private int id;

    @NotEmpty(message = "Name of the task is needed. Please write name of this task.")
    private String name;

    @NotEmpty(message = "Description of the task can not be empty. Please write short description of this task.")
    @Size(max = 1200, message = "Description of the task can be up to 1200 characters long. This description is longer. Please try to write shorter description.")
    private String description;

    @NotNull(message = "Date provided for completing this task is needed. Please choose the date.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;


    private boolean completed;

    private String creatorName;

    private UsersDTO owner;
}
