package com.example.EmployeeDirectoryService.Exceptions;

public class DomainNotFoundException extends RuntimeException{
    public DomainNotFoundException(String message){
        super(message);
    }
}
