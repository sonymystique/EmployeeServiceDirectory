package com.example.EmployeeDirectoryService.service;
import com.example.EmployeeDirectoryService.employeeDto.EmployeeDTO;
import com.example.EmployeeDirectoryService.entity.Employees;
import org.springframework.data.domain.Page;
import java.util.List;
public interface EmployeeService {
    public List<EmployeeDTO> getAllDetails();
    public List<EmployeeDTO> getFilteredDetails();
    public EmployeeDTO createEmployee(EmployeeDTO dto);
    public List<EmployeeDTO> getDomainByQuery(String domain);
    public List<EmployeeDTO> findEmployeesUsingSort(String field);
    public Page<Employees> findEmployeesUsingPaging(int offset);
    public EmployeeDTO updateEmployees(Long id, EmployeeDTO employeeDTO);
    public Boolean deleteEmployees(Long id);
}
