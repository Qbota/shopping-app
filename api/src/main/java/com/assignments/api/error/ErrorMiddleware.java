package com.assignments.api.error;

import com.assignments.api.error.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorMiddleware {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleException(ApiException exception){
        var apiError = new ApiError(exception);
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }
}
