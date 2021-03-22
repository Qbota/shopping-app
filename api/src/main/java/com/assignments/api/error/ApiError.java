package com.assignments.api.error;

import com.assignments.api.error.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public final class ApiError {

    private final LocalDateTime timestamp;
    private final HttpStatus httpStatus;
    private final String cause;
    private final String message;
    private final String detailedMessage;

    public ApiError(ApiException cause) {
        this.timestamp = LocalDateTime.now();
        this.cause = cause.getClass().getName();
        this.message = cause.getLocalizedMessage();
        this.httpStatus = cause.getHttpStatus();
        this.detailedMessage = cause.getDetailedMessage();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCause() {
        return cause;
    }

    public String getMessage() {
        return message;
    }

    public String getDetailedMessage() {
        return detailedMessage;
    }
}
