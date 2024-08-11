package com.example.tms.dto.tasks;

import lombok.Data;

@Data
public class TaskDto {
    private Integer pk;
    private Integer author;
    private Integer executor;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
}
