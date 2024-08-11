package com.example.tms.exception;

public class TaskNotFoundException extends RuntimeException {
    private final Integer id;

    public TaskNotFoundException(Integer id) {
        this.id = id;
    }
    @Override
    public String getMessage() {
        return "Ad is not found!";
    }
}
