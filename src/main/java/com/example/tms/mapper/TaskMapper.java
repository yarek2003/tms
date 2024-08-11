package com.example.tms.mapper;

import com.example.tms.dto.tasks.CreateOrUpdateTask;
import com.example.tms.dto.tasks.ExtendedTaskDto;
import com.example.tms.dto.tasks.TaskDto;
import com.example.tms.dto.tasks.TasksDto;
import com.example.tms.entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskMapper {
    public TaskDto toTaskDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setPk(task.getPk());
        taskDto.setDescription(task.getDescription());
        taskDto.setPriority(task.getPriority());
        taskDto.setTitle(task.getTitle());
        taskDto.setStatus(task.getStatus());
        taskDto.setAuthor(task.getAuthor().getId());
        taskDto.setExecutor(task.getExecutor().getId());
        return taskDto;
    }

    public Task toTaskEntity(CreateOrUpdateTask createOrUpdateTask) {
        Task task = new Task();
        task.setDescription(createOrUpdateTask.getDescription());
        task.setPriority(createOrUpdateTask.getPriority());
        task.setTitle(createOrUpdateTask.getTitle());
        task.setExecutor(createOrUpdateTask.getExecutor());
        return task;
    }

    public TasksDto toTasksDto(List<Task> tasks) {
        TasksDto tasksDto = new TasksDto();
        List<TaskDto> taskDtoList = tasks.stream().map(this::toTaskDto)
                .toList();
        return tasksDto;
    }
    public ExtendedTaskDto toExtendedTaskDto(Task task) {
        ExtendedTaskDto extendedTaskDto = new ExtendedTaskDto();
        extendedTaskDto.setPk(task.getPk());
        extendedTaskDto.setDescription(task.getDescription());
        extendedTaskDto.setPriority(task.getPriority());
        extendedTaskDto.setTitle(task.getTitle());
        extendedTaskDto.setStatus(task.getStatus());
        extendedTaskDto.setAuthor(task.getAuthor().getName());
        extendedTaskDto.setExecutor(task.getExecutor().getName());
        return extendedTaskDto;
    }
}
