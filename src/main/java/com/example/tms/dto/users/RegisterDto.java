package com.example.tms.dto.users;

import lombok.Data;

@Data
public class RegisterDto {

    private String email;
    private String password;
    private String name;
    private Role role;
}
