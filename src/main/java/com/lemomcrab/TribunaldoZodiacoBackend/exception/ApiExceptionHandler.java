package com.lemomcrab.TribunaldoZodiacoBackend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(StandardException.class)
    public ResponseEntity<String> handleStandardError(StandardException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }


}