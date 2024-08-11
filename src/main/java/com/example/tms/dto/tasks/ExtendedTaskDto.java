package com.example.tms.dto.tasks;

import lombok.Data;

@Data
public class ExtendedTaskDto {
    private Integer pk;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private String executor;
    private String author;
}
