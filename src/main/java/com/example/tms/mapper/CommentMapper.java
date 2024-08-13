package com.example.tms.mapper;

import com.example.tms.dto.comments.CommentDto;
import com.example.tms.dto.comments.CommentsDto;
import com.example.tms.dto.comments.CreateOrUpdateDto;
import com.example.tms.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentMapper {
    public CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setPk(comment.getPk());
        commentDto.setContent(comment.getContent());
        commentDto.setAuthor(comment.getAuthor().getId());
        commentDto.setAuthorName(comment.getAuthor().getName());
        return commentDto;
    }

    public Comment toCommentEntity(CreateOrUpdateDto createOrUpdateDto) {
        Comment comment = new Comment();
        comment.setContent(createOrUpdateDto.getContent());
        return comment;
    }

    public CommentsDto toCommentsDto(List<Comment> comments) {
        CommentsDto commentsDto = new CommentsDto();
        List<CommentDto> commentDtoList = comments.stream().toList().stream()
                .map(this::toCommentDto).toList();
        commentsDto.setCount(commentDtoList.size());
        commentsDto.setResult(commentDtoList);
        return commentsDto;
    }
}
