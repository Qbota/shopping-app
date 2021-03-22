package com.assignments.api.error.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends ApiException{

    public AuthenticationException(String detailedMessage) {
        super(HttpStatus.UNAUTHORIZED, detailedMessage);
    }
}
