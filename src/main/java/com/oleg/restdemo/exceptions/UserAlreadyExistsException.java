package com.oleg.restdemo.exceptions;

public class UserAlreadyExistsException extends IllegalArgumentException {
    public UserAlreadyExistsException(String name) {
        super("User with name " + name + " is already exists");
    }
}
