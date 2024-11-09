package com.example.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus responseHttpStatus;

    public CustomException(HttpStatus responseHttpStatus, String message) {
        super(message);
        this.responseHttpStatus = responseHttpStatus;
    }
}
