package com.assignments.api.error.exception;

import org.springframework.http.HttpStatus;

public class CodeGenerationException extends ApiException{

    public CodeGenerationException(Integer tries) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot generate unique code with %d retires".formatted(tries));
    }
}
