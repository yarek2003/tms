package com.example.tms.service;

import com.example.tms.dto.tasks.CreateOrUpdateTask;
import com.example.tms.dto.tasks.ExtendedTaskDto;
import com.example.tms.dto.tasks.TaskDto;
import com.example.tms.dto.tasks.TasksDto;
import com.example.tms.entity.Task;
import com.example.tms.entity.User;
import com.example.tms.exception.NotFoundException;
import com.example.tms.exception.TaskNotFoundException;
import com.example.tms.mapper.TaskMapper;
import com.example.tms.repository.TaskRepository;
import com.example.tms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor

public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public TaskDto addTask(CreateOrUpdateTask createOrUpdateTask, Authentication authentication) throws IOException {
        User   = userRepository.findByEmail(authentication.getName()).orElseThrow(RuntimeException::new);
        Task task = taskMapper.toTaskEntity(createOrUpdateTask);
        task.setExecutor(user);
        task.setAuthor(user);
        task = taskRepository.save(task);
        return taskMapper.toTaskDto(taskRepository.save(task));
    }


    public TasksDto getAll() {
        return taskMapper.toTasksDto(taskRepository.findAll());
    }


    public TasksDto getMyTasks(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return taskMapper.toTasksDto(taskRepository.findTasksByAuthorId(user.getId()));
    }


    public Task getTask(Integer id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public ExtendedTaskDto getExtendedTask(Integer id) {
        return taskMapper.toExtendedTaskDto(getTask(id));
    }


    public TaskDto updateTask(Integer id, CreateOrUpdateTask createOrUpdateTask, Authentication authentication) {
        Task task = getTask(id);
        task.setDescription(createOrUpdateTask.getDescription());
        task.setTitle(createOrUpdateTask.getTitle());
        task.setDescription(createOrUpdateTask.getDescription());
        task.setExecutor(createOrUpdateTask.getExecutor());
        task.setPriority(createOrUpdateTask.getPriority());
        return taskMapper.toTaskDto(taskRepository.save(task));
    }



    public void deleteTask(Integer id, Authentication authentication) throws NotFoundException {
        if (taskRepository.existsById(id)) {
            taskRepository.delete(getTask(id));
        } else {
            throw new NotFoundException();
        }
    }

}
