package com.assignments.api.error.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyInGroupException extends ApiException{

    public UserAlreadyInGroupException() {
        super(HttpStatus.BAD_REQUEST, "User already in group");
    }
}
