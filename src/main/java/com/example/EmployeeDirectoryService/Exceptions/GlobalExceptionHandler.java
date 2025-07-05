package com.example.EmployeeDirectoryService.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainNotFoundException.class)
    public ResponseEntity<?>
    handleEmailDomainNotFound(DomainNotFoundException ex){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error","Not Found");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleInvalidArgument(MethodArgumentNotValidException ex){
        Map<String, Object> errorMap = new HashMap<>();

        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error->{
            errorMap.put(error.getField(),error.getDefaultMessage());

        });
        return new ResponseEntity<>(errorMap,HttpStatus.BAD_REQUEST);
    }



}
