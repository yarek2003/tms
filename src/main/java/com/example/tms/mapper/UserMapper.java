package com.example.tms.mapper;

import com.example.tms.dto.users.RegisterDto;
import com.example.tms.dto.users.UserDto;
import com.example.tms.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        return userDto;
    }

    public User toUserEntity(RegisterDto registerDto) {
        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setRole(registerDto.getRole());
        return user;
    }

    public User toUserEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        return user;
    }
}
