package com.example.tms.dto.users;

import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class NewPasswordDto {
    @Size(min = 6, max = 20)
private String currentPassword;
    @Size(min = 6, max = 20)
private String newPassword;
}
