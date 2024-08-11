package com.example.tms.dto.users;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDto {
    @Size(min = 2, max = 20)
private String name;
}
