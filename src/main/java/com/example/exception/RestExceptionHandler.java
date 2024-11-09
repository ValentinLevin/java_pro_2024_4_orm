package com.example.exception;

import com.example.constant.RESPONSE_STATUS;
import com.example.dto.BaseResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    private ResponseEntity<BaseResponseDTO> buildResponseEntity(CustomException exception) {
        return ResponseEntity
                .status(exception.getResponseHttpStatus())
                .body(
                        new BaseResponseDTO(
                                RESPONSE_STATUS.ERROR.getStatus(),
                                exception.getMessage()
                        )
                );
    }

    @ExceptionHandler(ShelterNotFoundException.class)
    public ResponseEntity<BaseResponseDTO> handleShelterNotFoundException(ShelterNotFoundException ex) {
        return buildResponseEntity(ex);
    }

    @ExceptionHandler(SearchParamsParseException.class)
    public ResponseEntity<BaseResponseDTO> handleSearchParamsParseException(SearchParamsParseException ex) {
        return buildResponseEntity(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder strBuilder = new StringBuilder();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName;
            try {
                fieldName = ((FieldError) error).getField();
            } catch (ClassCastException ex) {
                fieldName = error.getObjectName();
            }
            String message = error.getDefaultMessage();
            strBuilder.append(String.format("%s: %s", fieldName, message));
        });

        return buildResponseEntity(new ValidationException(strBuilder.toString()));
    }
}
