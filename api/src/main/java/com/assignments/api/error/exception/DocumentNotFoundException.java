package com.assignments.api.error.exception;

import org.springframework.http.HttpStatus;

public class DocumentNotFoundException extends ApiException{

    public DocumentNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Cannot find requested document with given id");
    }
}
