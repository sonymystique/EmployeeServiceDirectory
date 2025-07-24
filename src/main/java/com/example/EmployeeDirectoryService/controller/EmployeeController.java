package com.example.EmployeeDirectoryService.controller;


import com.example.EmployeeDirectoryService.employeeDto.EmployeeDTO;
import com.example.EmployeeDirectoryService.entity.Employees;
import com.example.EmployeeDirectoryService.exceptions.EmployeeNotFoundException;
import com.example.EmployeeDirectoryService.exceptions.GlobalExceptionHandler;
import com.example.EmployeeDirectoryService.mapper.EmployeeMapper;
import com.example.EmployeeDirectoryService.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/employees")
public class EmployeeController {
    public static final String domain = "@mycompany.com";

    @Autowired
    private EmployeeService employeeService;

    @GetMapping()
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {

        List<EmployeeDTO> employeeDTOList = employeeService.getAllDetails();

        return new ResponseEntity<>(employeeDTOList, HttpStatus.OK);
    }

    //proper naming for the controller
    @GetMapping("/filter-by-domain")
    public ResponseEntity<List<EmployeeDTO>> getByFilter() {
        List<EmployeeDTO> employeeDTOList = employeeService.getFilteredDetails();
        return new ResponseEntity<>(employeeDTOList, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeDTO dto) {
        EmployeeDTO newEmployee = employeeService.createEmployee(dto);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/search-by-domain")
    public ResponseEntity<List<EmployeeDTO>> getDomainByQuery() {
        List<EmployeeDTO> employeesDTOList = employeeService.getDomainByQuery(domain);
        return new ResponseEntity<>(employeesDTOList, HttpStatus.OK);
    }

    //sorted list of employees with desc
    @GetMapping("/sorted-desc/{field}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeBySort(@PathVariable String field) {
        List<EmployeeDTO> employeeDTOList = employeeService.findEmployeesUsingSort(field);
        return new ResponseEntity<>(employeeDTOList, HttpStatus.OK);

    }

    @GetMapping("/page/{offset}/{pageSize}")
    public ResponseEntity<Page<Employees>> getEmployeeByPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<Employees> employeesList = employeeService.findEmployeesUsingPaging(offset, pageSize);
        return new ResponseEntity<>(employeesList, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployeeDetails(@PathVariable Long id, @Valid @RequestBody EmployeeDTO dto) {
        EmployeeDTO employeeDTO = employeeService.updateEmployees(id, dto);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
       employeeService.deleteEmployees(id);

        return new ResponseEntity<>("deleted employee with id :" + id, HttpStatus.OK);
    }

}
