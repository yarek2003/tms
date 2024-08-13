package com.example.tms.controller;

import com.example.tms.dto.comments.CommentDto;
import com.example.tms.dto.comments.CommentsDto;
import com.example.tms.dto.comments.CreateOrUpdateDto;
import com.example.tms.exception.NotFoundException;
import com.example.tms.repository.UserRepository;
import com.example.tms.service.CommentService;
import com.example.tms.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/tasks")
public class CommentController {
    private final UserRepository userRepository;
    private final CommentService commentService;
    private final TaskService taskService;

    public CommentController(UserRepository userRepository, CommentService commentService, TaskService taskService) {
        this.userRepository = userRepository;
        this.commentService = commentService;
        this.taskService = taskService;
    }

    @Operation(
            tags = "Комментарии",
            summary = "Получение комментариев объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentsDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/{id}/comments")
    //  @PreAuthorize(value = "hasRole('ADMIN') or @adServiceImpl.isAuthorAd(authentication, #adId)")
    public ResponseEntity<CommentsDto> getAllComments(@PathVariable Integer id, Authentication authentication) {
        if (authentication.getName() != null) {
            return ResponseEntity.ok(commentService.getComments(id,authentication));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(
            tags = "Комментарии",
            summary = "Добавление комментария к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content()
                    )
            }
    )

    @PostMapping("/{id}/comments")
    //   @PreAuthorize(value = "hasRole('ADMIN') or @adServiceImpl.isAuthorAd(authentication, #adId)")
    public ResponseEntity<CommentDto> addComment(@PathVariable("id") Integer id,
                                                 @RequestBody CreateOrUpdateDto createOrUpdateDto,
                                                 Authentication authentication) {
        return ResponseEntity.ok(commentService.addComment(id, createOrUpdateDto, authentication));
    }

    @Operation(
            tags = "Комментарии",
            summary = "Удаление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content()
                    )
            }
    )
    @DeleteMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("hasAuthority('ADMIN') or @commentService.getComment(#commentId).author.email == authentication.principal.username")
    public ResponseEntity<?> deleteComment (@PathVariable int adId, @PathVariable int commentId, Authentication authentication) {
        try {
            commentService.deleteComment(adId, commentId, authentication);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @Operation(
            tags = "Комментарии",
            summary = "Обновление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content()
                    )
            }
    )
    @PatchMapping("{adId}/comments/{commentId}")
    @PreAuthorize(value = "hasRole('ADMIN') or @taskService.isAuthorTask(authentication, #taskId)")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer adId, @PathVariable Integer commentId,
                                                    @RequestBody CreateOrUpdateDto createOrUpdateDto,
                                                    Authentication authentication) {
        try {
            commentService.updateComment(adId, commentId, createOrUpdateDto, authentication);
        } catch (NotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, createOrUpdateDto, authentication));
    }
}
