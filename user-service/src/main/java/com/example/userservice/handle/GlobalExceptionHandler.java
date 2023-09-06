package com.example.userservice.handle;

import com.example.userservice.exception.ApplicationException;
import com.example.userservice.model.dto.response.ExceptionResponse;
import com.example.userservice.model.enums.Exceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ExceptionResponse> handle(ApplicationException applicationException){
        Exceptions exception = applicationException.getException();

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(ExceptionResponse.builder()
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
