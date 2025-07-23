package com.example.EmployeeDirectoryService.Exceptions;

public class InvalidInputException extends RuntimeException{

    public InvalidInputException(String message){
        super(message);
    }
}
