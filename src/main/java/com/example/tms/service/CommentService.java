package com.example.tms.service;

import com.example.tms.dto.comments.CommentDto;
import com.example.tms.dto.comments.CommentsDto;
import com.example.tms.dto.comments.CreateOrUpdateDto;
import com.example.tms.entity.Comment;
import com.example.tms.entity.Task;
import com.example.tms.entity.User;
import com.example.tms.exception.NotFoundException;
import com.example.tms.mapper.CommentMapper;
import com.example.tms.repository.CommentRepository;
import com.example.tms.repository.TaskRepository;
import com.example.tms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;



    public CommentsDto getComments(Integer taskPk, Authentication authentication) {
        return commentMapper.toCommentsDto(commentRepository.findByTaskPk(taskPk));

    }


    public CommentDto addComment(Integer pk, CreateOrUpdateDto dto, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(RuntimeException::new);
        Task task = taskRepository.findById(pk).orElse(null);
        Comment comment = commentMapper.toCommentEntity(dto);
        comment.setAuthor(user);
        comment.setTask(task);
        comment.setContent(dto.getContent());
        comment.setCreatedAt(System.currentTimeMillis());
        return commentMapper.toCommentDto(commentRepository.save(comment));
    }


    public CommentDto updateComment(Integer taskPk,
                                    Integer commentId,
                                    CreateOrUpdateDto createOrUpdateDto,
                                    Authentication authentication) throws NotFoundException {
        if (commentRepository.existsById(commentId)){
            Comment comment = getComment(commentId);
            comment.setContent(createOrUpdateDto.getContent());
            return commentMapper.toCommentDto(commentRepository.save(comment));}
        else {
            throw new NotFoundException();}
    }

    public Comment getComment(Integer pk) {
        return commentRepository.findById(pk).orElseThrow();
    }

    public void deleteComment(Integer adId, Integer commentId, Authentication authentication) throws NotFoundException {
        if (commentRepository.existsById(commentId)) {
            commentRepository.delete(getComment(commentId));
        } else {
            throw new NotFoundException();
        }

    }
}
