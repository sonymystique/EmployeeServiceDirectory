package com.example.EmployeeDirectoryService.service;

import com.example.EmployeeDirectoryService.employeeDto.EmployeeDTO;
import com.example.EmployeeDirectoryService.entity.Employees;
import com.example.EmployeeDirectoryService.exceptions.DomainNotFoundException;
import com.example.EmployeeDirectoryService.exceptions.EmployeeNotFoundException;
import com.example.EmployeeDirectoryService.mapper.EmployeeMapper;
import com.example.EmployeeDirectoryService.repository.EmployeeRepository;
import com.example.EmployeeDirectoryService.service.impl.EmployeeServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


//@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
//
//
//
//
//    @Mock
//    private EmployeeRepository employeeRepository;
//    @InjectMocks
//    private EmployeeServiceImpl employeeService;
//
//
//    private Employees sampleEmployees;
//    private EmployeeDTO sampleEmployeeDTO;
//
//    @BeforeEach()
//    void setUp() {
//        sampleEmployees = new Employees(1L, "name1", "name1@gmail.com");
//        sampleEmployeeDTO = new EmployeeDTO("name2@gmail.com", "name2");
//    }
//
//    @Test
//    void createEmployeeTest() {
//        when(employeeRepository.save(Mockito.any(Employees.class))).thenReturn(sampleEmployees);
//
//        EmployeeDTO result = employeeService.createEmployee(sampleEmployeeDTO);
//
//        assertNotNull(result);
//        assertEquals("name1", result.getFullName());
//        assertEquals("name1@gmail.com", result.getEmail());
//
//
//
//    }
//
//
//
//
//
//    @Test
//    void testGetAllDetails() {
//        List<Employees> employees = List.of(new Employees(2L, "name2", "name2@gmail.com"), new Employees(1L, "name1", "name1@gmail.com"));
//        List<EmployeeDTO> employeeDTO = List.of(new EmployeeDTO("name2@gmail.com", "name2"), new EmployeeDTO("name1@gmail.com", "name1"));
//
//        when(employeeRepository.findAll()).thenReturn(employees);
//
//        List<EmployeeDTO> result = employeeService.getAllDetails();
//
//        //System.out.println(employeeDTOS);
//        assertNotNull(result);
//        assertEquals(2, result.size());
//
//    }
//
//    @Test
//    void testGetAllDetails_NOT_FOUND() {
//        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());
//        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getAllDetails());
//    }
//
//    @Test
//    void testGetFilteredDetails() {
//        List<Employees> employees = List.of(new Employees(2L, "name2", "name2@mycompany.com"), new Employees(1L, "name1", "name1@gmail.com"));
//        List<EmployeeDTO> employeeDTO = List.of(new EmployeeDTO("name2", "name2@mycompany.com"));
//
//        when(employeeRepository.findAll()).thenReturn(employees);
//
//        List<EmployeeDTO> result = employeeService.getFilteredDetails();
//
//        assertNotNull(result);
//
//        assertEquals(result, employeeDTO);
//        assertEquals(result.size(), 1);
//        assertEquals(result.getFirst().getEmail(), "name2@mycompany.com");
//
//    }
//    @Test
//    void testGetFilteredDetails_NOTFOUND(){
//        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());
//        assertThrows(DomainNotFoundException.class,()->employeeService.getFilteredDetails());
//    }
//
//    @Test
//    void testgetDomainByQuery() {
//
//        List<EmployeeDTO> employeeDTOList = List.of(new EmployeeDTO("name2", "name2@mycompany.com"));
//        List<Employees> employees = List.of(new Employees(2L, "name2", "name2@mycompany.com"));
//
//        when(employeeRepository.getEmployeeWithDomain(anyString())).thenReturn(employees);
//
//        List<EmployeeDTO> result = employeeService.getDomainByQuery("@mycompany");
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(result, employeeDTOList);
//
//    }
//
//    @Test
//    void testgetDomainByQuery_NOTFOUND(){
//        when(employeeRepository.getEmployeeWithDomain(anyString())).thenReturn(Collections.emptyList());
//        assertThrows(EmployeeNotFoundException.class,()->employeeService.getDomainByQuery("@mycompany"));
//    }
//
//    @Test
//    void testFindEmployeeUsingSort() {
//        List<Employees> employeesList = List.of(new Employees(4, "name4", "name4@mycompany.com"),
//                new Employees(3, "name3", "name3@mycompany.com"),
//                new Employees(2, "name2", "name2@mycompany.com"),
//                new Employees(1, "name1", "name1@mycompany.com")
//
//        );
//        List<EmployeeDTO> employeeDTOList = List.of(new EmployeeDTO("name4", "name4@mycompany.com"),
//                new EmployeeDTO("name3", "name3@mycompany.com"),
//                new EmployeeDTO("name2", "name2@mycompany.com"),
//                new EmployeeDTO("name1", "name1@mycompany.com")
//
//        );
//
//        Sort sort = Sort.by(Sort.Direction.DESC, "id");
//        when(employeeRepository.findAll(sort)).thenReturn(employeesList);
//
//
//        List<EmployeeDTO> result = employeeService.findEmployeesUsingSort("id");
//
//        assertNotNull(result);
//        assertEquals(4, result.size());
//        assertEquals(result, employeeDTOList);
//
//    }
//
//    @Test
//    void testFindEmployeeUsingSort_NOTFOUND(){
//        Sort sort = Sort.by(Sort.Direction.DESC, "id");
//        when(employeeRepository.findAll(sort)).thenReturn(Collections.emptyList());
//        assertThrows(EmployeeNotFoundException.class,()->employeeService.findEmployeesUsingSort("id"));
//    }
//
//
//    @Test
//    void testFindEmployeeUsingPaging() {
//        List<Employees> employeesList = List.of(new Employees(1, "name1", "name1@mycompany.com"),
//                new Employees(2, "name2", "name2@mycompany.com"),
//                new Employees(3, "name3", "name3@mycompany.com"),
//                new Employees(4, "name4", "name4@mycompany.com"),
//                new Employees(5, "name5", "name5@mycompany.com"),
//                new Employees(6, "name6", "name6@mycompany.com")
//        );
//
//        Pageable pageable = PageRequest.of(0, 5);
//        PageImpl page = new PageImpl<>(employeesList, pageable, employeesList.size());
//
//        PageRequest pageRequest = PageRequest.of(0, 5);
//        when(employeeRepository.findAll(pageRequest)).thenReturn((Page<Employees>) page);
//
//        Page<Employees> result = employeeService.findEmployeesUsingPaging(0, 5);
//
//        assertNotNull(result);
//        assertEquals(5, result.getSize());
//        assertEquals(page, result);
//
//    }

//    void testFindEmployeeUsingPaging_NOTFOUND(){
//        PageRequest pageRequest = PageRequest.of(0, 5);
//        Pageable pageable;
//        pageable.
//
//        when(employeeRepository.findAll(pageRequest)).thenReturn(Pageable.)
//    }

//    @Test
//    void testUpdateEmployee() {
//        Mockito.when()
//    }


}
