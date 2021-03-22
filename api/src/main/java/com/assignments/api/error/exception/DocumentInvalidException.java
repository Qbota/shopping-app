package com.assignments.api.error.exception;

import org.springframework.http.HttpStatus;

public class DocumentInvalidException extends ApiException{

    public DocumentInvalidException(String detailedMessage) {
        super(HttpStatus.BAD_REQUEST, detailedMessage);
    }
}
