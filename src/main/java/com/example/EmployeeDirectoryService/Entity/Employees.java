package com.example.EmployeeDirectoryService.Entity;

import jakarta.persistence.*;

@Entity

public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String EmployeeEmail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeEmail() {
        return EmployeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        EmployeeEmail = employeeEmail;
    }

    public Employees() {
    }

    public Employees(Long id, String name, String employeeEmail) {
        this.id = id;
        this.name = name;
        EmployeeEmail = employeeEmail;
    }

    @Override
    public String toString() {
        return "Employees{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", EmployeeEmail='" + EmployeeEmail + '\'' +
                '}';
    }
}
