package com.example.EmployeeDirectoryService.Repository;

import com.example.EmployeeDirectoryService.Entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employees, Long> {
}
