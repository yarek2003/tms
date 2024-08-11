package com.example.tms.dto.tasks;

import lombok.Data;

import java.util.List;

@Data
public class TasksDto {
private Integer count;
private List<TaskDto> results;
}
