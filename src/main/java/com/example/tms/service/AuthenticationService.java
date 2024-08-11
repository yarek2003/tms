package com.example.tms.service;

import com.example.tms.dto.users.JwtAuthenticationResponse;
import com.example.tms.dto.users.LoginDto;
import com.example.tms.dto.users.RegisterDto;
import com.example.tms.exception.UserAlreadyRegisteredException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.tms.repository.UserRepository;

import com.example.tms.entity.User;

@Service
@RequiredArgsConstructor
public class AuthenticationService  {
    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(RegisterDto registerDto) {

        if(userRepository.findByEmail(registerDto.getEmail()).isPresent()) {

            throw new UserAlreadyRegisteredException(registerDto.getEmail());

        }
        userService.registerUser(registerDto);

        var user = userService.userDetailsService().loadUserByUsername(registerDto.getEmail());
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(LoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(loginDto.getEmail());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
