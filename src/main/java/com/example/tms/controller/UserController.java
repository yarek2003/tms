package com.example.tms.controller;

import com.example.tms.dto.users.NewPasswordDto;
import com.example.tms.dto.users.UpdateUserDto;
import com.example.tms.dto.users.UserDto;
import com.example.tms.exception.PasswordIsNotCorrectException;
import com.example.tms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@Valid @RequestBody NewPasswordDto dto, Authentication authentication) {
        try {
            userService.updatePassword(dto, authentication.getName());
        } catch (PasswordIsNotCorrectException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dto);
        }

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        return ResponseEntity.ok(userService.getInfoAboutMe(authentication.getName()));
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDto> updateUser(@Valid @RequestBody UpdateUserDto dto, Authentication authentication) {
        return ResponseEntity.ok(userService.updateInfoAboutMe(authentication.getName(), dto));
    }





}
