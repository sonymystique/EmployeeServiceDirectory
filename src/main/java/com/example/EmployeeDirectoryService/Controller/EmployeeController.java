package com.example.EmployeeDirectoryService.Controller;


import ch.qos.logback.core.model.conditional.ElseModel;
import com.example.EmployeeDirectoryService.EmployeeDTO.EmployeeDTO;
import com.example.EmployeeDirectoryService.Entity.Employees;
import com.example.EmployeeDirectoryService.Service.EmployeeServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/Employees")
public class EmployeeController {

    @Autowired
    private EmployeeServiceInterface employeeServiceInterface;

    @GetMapping("/getAll")
    public List<EmployeeDTO> getAllEmployees(){
        return employeeServiceInterface.getAllDetails();
    }

    @GetMapping("/getByFilter")
    public List<EmployeeDTO> getByFilter(){
        return employeeServiceInterface.getFilteredDetails();
    }

    @PostMapping("/addEmployeeDetails")
    public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeDTO dto){
        EmployeeDTO newEmployee = employeeServiceInterface.createEmployee(dto);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/getDomainByQuery")
    public List<Employees> getDomainByQuery(){
        String domain = "@mycompany.com";
        return employeeServiceInterface.getDomainByQuery(domain);
    }

    @GetMapping("/getBySort/{field}")
    public List<Employees> getEmployeeBySort(@PathVariable String field){
        return employeeServiceInterface.findEmployeesUsingSort(field);

    }

    @GetMapping("/getByPagination/{offset}/{pageSize}")
    public Page<Employees> getEmployeeByPagination(@PathVariable int offset, @PathVariable int pageSize){
        return employeeServiceInterface.findEmployeesUsingPaging(offset, pageSize);
    }







}
