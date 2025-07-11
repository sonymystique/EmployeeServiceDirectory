package com.example.EmployeeDirectoryService.Service;

import com.example.EmployeeDirectoryService.EmployeeDTO.EmployeeDTO;
import com.example.EmployeeDirectoryService.Entity.Employees;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface EmployeeServiceInterface {

    public List<EmployeeDTO> getAllDetails();
    public List<EmployeeDTO> getFilteredDetails();
    public EmployeeDTO createEmployee(EmployeeDTO dto);
    public List<Employees> getDomainByQuery(String domain);
    public List<Employees> findEmployeesUsingSort(String field);
    public Page<Employees> findEmployeesUsingPaging(int offset, int PagSize);
}
