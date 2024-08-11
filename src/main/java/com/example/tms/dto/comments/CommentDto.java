package com.example.tms.dto.comments;

import lombok.Data;

@Data
public class CommentDto {
    Integer pk;
    private Integer author;
    private String authorName;
    private String content;
    Long createdAt;
}
