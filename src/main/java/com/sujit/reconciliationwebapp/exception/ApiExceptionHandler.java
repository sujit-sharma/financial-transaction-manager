package com.sujit.reconciliationwebapp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Set;

@Component
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Set<ApiError>> handle(ViolationException exception) {
        return ResponseEntity.badRequest().body(exception.getErrors());
    }
    @ExceptionHandler
    public ResponseEntity<?> handle(EntityNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
}
