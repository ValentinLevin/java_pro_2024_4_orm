package com.example.exception;

import org.springframework.http.HttpStatus;

public class SearchParamsParseException extends CustomException {
    private static final String MESSAGE = "Error parsing search params: field name: %s, filter param: %s";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public SearchParamsParseException(String fieldName, String value) {
        super(STATUS, String.format(MESSAGE, fieldName, value));
    }
}
