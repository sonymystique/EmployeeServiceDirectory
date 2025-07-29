package com.example.EmployeeDirectoryService.controller;
import com.example.EmployeeDirectoryService.employeeDto.EmployeeDTO;
import com.example.EmployeeDirectoryService.service.EmployeeService;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
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
import static com.example.EmployeeDirectoryService.constants.domain;
@RestController
@RequestMapping("/ems")
public class EmployeeController {
    private EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/employees/filterByDomain")
    public ResponseEntity<List<EmployeeDTO>> getByFilter() {
        return new ResponseEntity<>(employeeService.getFilteredDetails(), HttpStatus.OK);
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeDTO dto) {
        return new ResponseEntity<>(employeeService.createEmployee(dto), HttpStatus.CREATED);
    }
    @GetMapping("/employees/domainByQuery")
    public ResponseEntity<List<EmployeeDTO>> getDomainByQuery() {
        return new ResponseEntity<>(employeeService.getDomainByQuery(domain), HttpStatus.OK);
    }
    //sorted list of employees with desc
    @GetMapping("/employees/sortedDesc/{field}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeBySort(@PathVariable String field) {
        return new ResponseEntity<>(employeeService.findEmployeesUsingSort(field), HttpStatus.OK);
    }
    // make this optional
    @GetMapping("/employees")
    public ResponseEntity<Object> getEmployeeByPagination(@RequestParam(required = false) Integer offset) {
        if(offset!=null) {
            Response response ;
            return new ResponseEntity<>(employeeService.findEmployeesUsingPaging(offset), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(employeeService.getAllDetails(), HttpStatus.OK);
        }
    }
    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployeeDetails(@PathVariable Long id, @Valid @RequestBody EmployeeDTO dto) {
        return new ResponseEntity<>(employeeService.updateEmployees(id, dto), HttpStatus.OK);
    }
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployees(id);
        return new ResponseEntity<>("deleted employee with id :" + id, HttpStatus.OK);
    }
}
