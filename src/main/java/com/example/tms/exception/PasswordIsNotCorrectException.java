package com.example.tms.exception;

public class PasswordIsNotCorrectException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Current password is not correct :(";
    }
}
