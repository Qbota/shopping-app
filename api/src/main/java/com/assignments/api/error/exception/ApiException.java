package com.assignments.api.error.exception;

import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException{

    private final HttpStatus httpStatus;
    private final String detailedMessage;

    public ApiException(HttpStatus httpStatus, String detailedMessage) {
        this.httpStatus = httpStatus;
        this.detailedMessage = detailedMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getDetailedMessage() {
        return detailedMessage;
    }
}
