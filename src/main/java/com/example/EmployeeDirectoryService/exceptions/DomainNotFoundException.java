package com.example.EmployeeDirectoryService.exceptions;
public class DomainNotFoundException extends RuntimeException{
    public DomainNotFoundException(String message){
        super(message);
    }
}
