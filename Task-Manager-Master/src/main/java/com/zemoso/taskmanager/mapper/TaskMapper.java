package com.zemoso.taskmanager.mapper;

import com.zemoso.taskmanager.dto.TaskDTO;
import com.zemoso.taskmanager.entity.Task;
import com.zemoso.taskmanager.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "task.owner", target = "owner")
    TaskDTO taskToTaskDTO(Task task);

    List<TaskDTO> taskListToTaskDTOList(List<Task> taskList);

    @Mapping(target = "owner", expression = "java(null)")
    Task taskDTOToTask(TaskDTO taskDTO);


    default String map(Users user){
        if(user == null){
            return null;
        }
         return user.getUsername();
    }
}
