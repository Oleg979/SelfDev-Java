package com.oleg.restdemo.exceptions;

public class UsernameNotFoundException extends IllegalArgumentException {
    public UsernameNotFoundException(String name) {
        super("User with name " + name + " is not found");
    }
}
