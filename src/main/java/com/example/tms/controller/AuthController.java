package com.example.tms.controller;

import com.example.tms.dto.users.JwtAuthenticationResponse;
import com.example.tms.dto.users.LoginDto;
import com.example.tms.dto.users.RegisterDto;
import com.example.tms.service.AuthenticationService;
import com.example.tms.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;


    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid RegisterDto registerDto) {
        return authenticationService.signUp(registerDto);
    }


    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid LoginDto loginDto) {
        return authenticationService.signIn(loginDto);
    }
}
