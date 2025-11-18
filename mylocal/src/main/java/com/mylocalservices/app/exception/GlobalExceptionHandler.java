package com.mylocalservices.app.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex) {
        return new ErrorResponse(
                ex.getMessage(),
                "NOT_FOUND",
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new ErrorResponse(msg, "VALIDATION_ERROR", LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGeneric(Exception ex) {
        return new ErrorResponse(
                ex.getMessage(),
                "INTERNAL_ERROR",
                LocalDateTime.now()
        );
    }
}
