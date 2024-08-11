package com.example.tms.exception;

public class UserAlreadyRegisteredException extends RuntimeException  {

    private final String email;

    public UserAlreadyRegisteredException(String email) {
        this.email = email;
    }

    @Override
    public String getMessage() {
        return "User with email - " + email + " already registered";
    }
}
