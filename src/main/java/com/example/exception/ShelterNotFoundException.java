package com.example.exception;

import org.springframework.http.HttpStatus;

public class ShelterNotFoundException extends CustomException {
    private static final String NOT_FOUND_BY_ID_MESSAGE = "Shelter with id %s not found";
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public ShelterNotFoundException(String id) {
        super(STATUS, String.format(NOT_FOUND_BY_ID_MESSAGE, id));
    }
}
