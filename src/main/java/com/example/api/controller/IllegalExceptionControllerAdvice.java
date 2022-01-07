package com.example.api.controller;

import com.example.api.exception.IllegalProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = IllegalProcessingException.class)
public class IllegalExceptionControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleOtherException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
    }
}
