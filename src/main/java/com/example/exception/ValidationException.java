package com.example.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends CustomException {
    private static final String VALIDATION_ERROR_EXCEPTION = "Validation errors: [%s]";
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public ValidationException(String errors) {
        super(STATUS, String.format(VALIDATION_ERROR_EXCEPTION, errors));
    }
}
