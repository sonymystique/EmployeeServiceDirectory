package com.example.EmployeeDirectoryService.controller;

import com.example.EmployeeDirectoryService.employeeDto.EmployeeDTO;
import com.example.EmployeeDirectoryService.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
@RequestMapping("/ems")
@AllArgsConstructor
@Tag(name="Employee APIs", description = "Crud Operations on Employee ")
public class EmployeeController {
    private EmployeeService employeeService;


    @GetMapping("/employees/filterByDomain")
    public ResponseEntity<List<EmployeeDTO>> getByFilter() {
        return new ResponseEntity<>(employeeService.getFilteredDetails(), HttpStatus.OK);
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeDTO dto) {

        return new ResponseEntity<>(employeeService.createEmployee(dto), HttpStatus.CREATED);
    }

    // make this optional
    @GetMapping("/employees")
    public ResponseEntity<Object> getEmployeeByPagination(@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer page) {
        if (offset != null && page != null) {
            return new ResponseEntity<>(employeeService.findEmployeesUsingPaging(offset, page), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(employeeService.getAllDetails(), HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_ADMIN')")
    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployeeDetails(@PathVariable Long id, @Valid @RequestBody EmployeeDTO dto) {
        return new ResponseEntity<>(employeeService.updateEmployees(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {

        return new ResponseEntity<>(employeeService.deleteEmployees(id), HttpStatus.OK);
    }
}
