package com.oleg.restdemo.exceptions;

public class TaskNotFoundException extends IllegalArgumentException {
    public TaskNotFoundException(Long id) {
        super("Task with id " + id + " is not found");
    }
}
