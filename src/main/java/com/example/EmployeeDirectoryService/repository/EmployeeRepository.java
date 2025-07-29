package com.example.EmployeeDirectoryService.repository;
import com.example.EmployeeDirectoryService.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface EmployeeRepository extends JpaRepository<Employees, Long> {
    @Query(value = "SELECT * FROM employees WHERE employee_email LIKE '%@mycompany.com' ", nativeQuery = true)
    public List<Employees> getEmployeeWithDomain(String domain);
}
