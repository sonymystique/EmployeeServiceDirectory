package com.example.EmployeeDirectoryService.exceptions;

public class InvalidInputException extends RuntimeException{

    public InvalidInputException(String message){
        super(message);
    }
}
