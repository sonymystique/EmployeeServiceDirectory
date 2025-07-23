package com.example.EmployeeDirectoryService.Controller;


import ch.qos.logback.core.model.conditional.ElseModel;
import com.example.EmployeeDirectoryService.EmployeeDTO.EmployeeDTO;
import com.example.EmployeeDirectoryService.Entity.Employees;
import com.example.EmployeeDirectoryService.Exceptions.DomainNotFoundException;
import com.example.EmployeeDirectoryService.Exceptions.EmployeeNotFoundException;
import com.example.EmployeeDirectoryService.Exceptions.GlobalExceptionHandler;
import com.example.EmployeeDirectoryService.Mapper.EmployeeMapper;
import com.example.EmployeeDirectoryService.Service.EmployeeServiceInterface;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/employees")
public class EmployeeController {
    public static final String domain = "@mycompany.com";

    @Autowired
    private EmployeeServiceInterface employeeServiceInterface;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @GetMapping("/getEmployees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){

        List<EmployeeDTO> employeeDTOList= employeeServiceInterface.getAllDetails();
            try {
                if (employeeDTOList == null) {
                    throw new EmployeeNotFoundException("Employee not found Database is empty");
                }
            }catch (EmployeeNotFoundException ex){
                throw new EmployeeNotFoundException("Employee not found Database is empty");
            }
        return new ResponseEntity<>(employeeDTOList,HttpStatus.FOUND);
    }

    @GetMapping("/getEmployees/ByFilter")
    public ResponseEntity<List<EmployeeDTO>> getByFilter(){
        List<EmployeeDTO>  employeeDTOList=employeeServiceInterface.getFilteredDetails();
        try{
            if(employeeDTOList == null){
                throw new EmployeeNotFoundException("No employee found with the given filter");
            }
        }catch (Exception ex){
            throw new EmployeeNotFoundException("No employee found with the given filter");
        }
        return new ResponseEntity<>(employeeDTOList,HttpStatus.FOUND);
    }

    @PostMapping("/postEmployees")
    public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeDTO dto){
        EmployeeDTO newEmployee = employeeServiceInterface.createEmployee(dto);

        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/getEmployees/UsingQuery")
    public ResponseEntity<List<EmployeeDTO>> getDomainByQuery(){

        List<Employees> employeesList = employeeServiceInterface.getDomainByQuery(domain);
        List<EmployeeDTO> employeeDTOList = EmployeeMapper.instance.toDTOList(employeesList);

        try {
            if(employeeDTOList==null){
                throw new EmployeeNotFoundException("No employee found");
            }
        }catch (EmployeeNotFoundException ex){
            throw new EmployeeNotFoundException("No employee found");
        }
        return new ResponseEntity<>(employeeDTOList,HttpStatus.FOUND);
    }

    @GetMapping("/getEmployees/BySort/{field}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeBySort(@PathVariable String field){
        List<Employees> employeesList = employeeServiceInterface.findEmployeesUsingSort(field);
        List<EmployeeDTO> employeeDTOList = EmployeeMapper.instance.toDTOList(employeesList);

        try{
            if(employeeDTOList==null){
                throw new EmployeeNotFoundException("No employees found");
            }
        }catch(EmployeeNotFoundException ex){
            throw new EmployeeNotFoundException("No employees found");
        }
        return new ResponseEntity<>(employeeDTOList,HttpStatus.FOUND);

    }

    @GetMapping("/getEmployees/ByPagination/{offset}/{pageSize}")
    public ResponseEntity<Page<Employees>> getEmployeeByPagination(@PathVariable int offset, @PathVariable int pageSize){
        Page<Employees> employeesList = employeeServiceInterface.findEmployeesUsingPaging(offset, pageSize);

        try{
            if(employeesList==null){
                throw new EmployeeNotFoundException("No employees found");
            }
        }catch (EmployeeNotFoundException ex){
            throw new EmployeeNotFoundException("No employees found");
        }

        return new ResponseEntity<>(employeesList,HttpStatus.OK);

    }

    @PutMapping("/UpdateEmployees")
    public ResponseEntity<EmployeeDTO> updateEmployeeDetails(@PathVariable int id,@Valid @RequestBody EmployeeDTO dto){
        EmployeeDTO employeeDTO = employeeServiceInterface.updateEmployees(id, dto);
        return new ResponseEntity<>(employeeDTO,HttpStatus.ACCEPTED);
    }

}
