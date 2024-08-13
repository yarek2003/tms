package com.example.tms.controller;

import com.example.tms.dto.comments.CommentDto;
import com.example.tms.dto.comments.CreateOrUpdateDto;
import com.example.tms.dto.tasks.CreateOrUpdateTask;
import com.example.tms.dto.tasks.ExtendedTaskDto;
import com.example.tms.dto.tasks.TaskDto;
import com.example.tms.dto.tasks.TasksDto;
import com.example.tms.exception.NotFoundException;
import com.example.tms.service.CommentService;
import com.example.tms.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final CommentService commentService;


    @GetMapping
    public ResponseEntity<TasksDto> getAllTasks() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @GetMapping("/me")
    public ResponseEntity<TasksDto> getAdsMe(Authentication authentication) {
        TasksDto dto = taskService.getMyTasks(authentication.getName());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedTaskDto> getTask(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.getExtendedTask(id));
    }


    @PostMapping("/{id}")
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer id, @RequestPart(value = "properties") CreateOrUpdateDto createOrUpdateDto,

                                            Authentication authentication) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(id, createOrUpdateDto,  authentication));
    }

    @PreAuthorize("hasAuthority('ADMIN') or @taskService.getTask(#id).author.email == authentication.principal.username")
    @PatchMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Integer id,
                                          @RequestBody CreateOrUpdateTask createOrUpdateTask,
                                          Authentication authentication) {
        return ResponseEntity.ok(taskService.updateTask(id, createOrUpdateTask, authentication));
    }



    @PreAuthorize("hasAuthority('ADMIN') or @taskService.getTask(#id).author.email == authentication.principal.username")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAd(@PathVariable Integer id, Authentication authentication) {
        try {
            taskService.deleteTask(id, authentication);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
