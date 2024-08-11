package com.example.tms.dto.tasks;

import com.example.tms.entity.User;
import lombok.Data;

@Data
public class CreateOrUpdateTask {
    private User executor;
    private String title;
    private String description;
    private Priority priority;
}
