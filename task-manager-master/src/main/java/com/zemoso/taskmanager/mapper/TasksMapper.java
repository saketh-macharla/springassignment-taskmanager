package com.zemoso.taskmanager.mapper;

import com.zemoso.taskmanager.dto.TasksDTO;
import com.zemoso.taskmanager.entity.Task;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD,
        uses = {UsersMapper.class})
public interface TasksMapper {

    TasksMapper INSTANCE = Mappers.getMapper(TasksMapper.class);

    TasksDTO tasksToTasksDTO(Task task);

    List<TasksDTO> tasksListToTasksDTOList(List<Task> tasks);

    Task tasksDTOToTasks(TasksDTO tasksDTO);
}
