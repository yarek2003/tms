package com.example.tms.service;

import com.example.tms.dto.users.NewPasswordDto;
import com.example.tms.dto.users.RegisterDto;
import com.example.tms.dto.users.UpdateUserDto;
import com.example.tms.dto.users.UserDto;
import com.example.tms.exception.PasswordIsNotCorrectException;
import com.example.tms.exception.UserAlreadyRegisteredException;
import com.example.tms.mapper.UserMapper;
import com.example.tms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.tms.entity.User;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder encoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.encoder = encoder;
    }

    public void updatePassword(NewPasswordDto dto, String username) throws PasswordIsNotCorrectException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User is not found"));
        if (encoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            user.setPassword(encoder.encode(dto.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new PasswordIsNotCorrectException();
        }
    }

    public UserDto getByName(String name) {
        User user = userRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException("User is not found")); //TODO: возможно тут никогда не будет исключения
        return userMapper.toUserDto(user);
    }
    public UserDetailsService userDetailsService() {
        return name -> (UserDetails) userMapper.toUserEntity(getByName(name));
    }
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userMapper.toUserEntity(getByName(name));
    }

    public UserDto getInfoAboutMe(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User is not found")); //TODO: возможно тут никогда не будет исключения
        return userMapper.toUserDto(user);
    }
    public UpdateUserDto updateInfoAboutMe(String username, UpdateUserDto dto) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User is not found")); //TODO: возможно тут никогда не будет исключения
        user.setName(dto.getName());
        userRepository.save(user);
        return dto;
    }
    public User registerUser(RegisterDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserAlreadyRegisteredException(dto.getEmail());
        } else {
            User user = userMapper.toUserEntity(dto);
            user.setPassword(encoder.encode(dto.getPassword()));
            return userRepository.save(user);
        }
    }
}
