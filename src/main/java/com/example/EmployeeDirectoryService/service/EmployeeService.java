package com.example.EmployeeDirectoryService.service;

import com.example.EmployeeDirectoryService.employeeDto.EmployeeDTO;
import com.example.EmployeeDirectoryService.entity.Employees;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    public List<EmployeeDTO> getAllDetails();

    public List<EmployeeDTO> getFilteredDetails();

    public EmployeeDTO createEmployee(EmployeeDTO dto);

    public Page<Employees> findEmployeesUsingPaging(int offset, int page);

    public EmployeeDTO updateEmployees(Long id, EmployeeDTO employeeDTO);

    public Optional<Employees> deleteEmployees(Long id);
}
