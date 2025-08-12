package com.example.EmployeeDirectoryService.service;

import com.example.EmployeeDirectoryService.employeeDto.EmployeeDTO;
import com.example.EmployeeDirectoryService.entity.Employees;
import com.example.EmployeeDirectoryService.exceptions.DomainNotFoundException;
import com.example.EmployeeDirectoryService.exceptions.EmployeeNotFoundException;
import com.example.EmployeeDirectoryService.exceptions.InvalidInputException;
import com.example.EmployeeDirectoryService.mapper.EmployeeMapper;
import com.example.EmployeeDirectoryService.repository.EmployeeRepository;
import com.example.EmployeeDirectoryService.service.impl.EmployeeServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
//    @Mock
//    private EmployeeRepository employeeRepository;
//    @Mock
//    private EmployeeMapper employeeMapper;
//    @InjectMocks
//    private EmployeeServiceImpl employeeService;
//
//
//    private Employees sampleEmployees;
//    private EmployeeDTO sampleEmployeeDTO;
//    private List<Employees> employees;
//    private List<EmployeeDTO> employeeDTO;
//
//
//    @BeforeEach()
//    void setUp() {
//        sampleEmployees = new Employees(1L, "name1", "name1@gmail.com",
//                LocalDateTime.now(),LocalDateTime.now());
//        sampleEmployeeDTO = new EmployeeDTO("name2", "name2@gmail.com",
//                LocalDateTime.now(),LocalDateTime.now());
//       employees = List.of(new Employees(2L, "name2", "name2@mycompany.com",LocalDateTime.now(),LocalDateTime.now()),
//                new Employees(1L, "name1", "name1@mycompany.com",LocalDateTime.now(), LocalDateTime.now()));
//       employeeDTO = List.of(new EmployeeDTO("name2", "name2@mycompany.com",LocalDateTime.now(), LocalDateTime.now()),
//                new EmployeeDTO("name1", "name1@mycompany.com",LocalDateTime.now(), LocalDateTime.now()));
//    }
//
//    @Test
//    void createEmployeeTest() {
//        when(employeeRepository.save(any(Employees.class))).thenReturn(sampleEmployees);
//
//        EmployeeDTO result = employeeService.createEmployee(sampleEmployeeDTO);
//
//        assertNotNull(result);
//        assertEquals("name1", result.getFullName());
//        assertEquals("name1@gmail.com", result.getEmail());
//
//    }
//    // not done
//    @Test
//    void createEmployeeTest_NOTFOUND() {
//        EmployeeDTO nullDto = null;
////        when(employeeRepository.save(nullEmployee)).thenReturn(null);
////        assertThrows(InvalidInputException.class, () -> employeeService.createEmployee(nullDto));
//        assertThrows(InvalidInputException.class,()->employeeService.createEmployee(nullDto));
//
//        verify(employeeRepository, never()).save(any(Employees.class));
//        verify(employeeMapper, never()).toEntity(any(EmployeeDTO.class));
//        verify(employeeMapper, never()).toDTO(any(Employees.class));
//
//    }
//     //done
//    @Test
//    void testGetAllDetails() {
//
//
//        when(employeeRepository.findAll()).thenReturn(employees);
//
//        List<EmployeeDTO> result = employeeService.getAllDetails();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//
//    }
//    //done
//    @Test
//    void testGetAllDetails_NOT_FOUND() {
//        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());
//        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getAllDetails());
//    }
//
//    @Test
//    void testGetFilteredDetails() {
//
//
//        when(employeeRepository.findAll()).thenReturn(employees);
//        List<EmployeeDTO> result = employeeService.getFilteredDetails();
//        assertNotNull(result);
//        assertEquals(result, employeeDTO);
//
//
//    }
//    @Test
//    void testGetFilteredDetails_NOTFOUND(){
//        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());
//        assertThrows(DomainNotFoundException.class,()->employeeService.getFilteredDetails());
//    }
//
//
//    @Test
//    void testFindEmployeeUsingPaging() {
//        List<Employees> employeesList = List.of(new Employees(1, "name1", "name1@mycompany.com",LocalDateTime.now(),LocalDateTime.now()),
//                new Employees(2, "name2", "name2@mycompany.com",LocalDateTime.now(),LocalDateTime.now()),
//                new Employees(3, "name3", "name3@mycompany.com",LocalDateTime.now(),LocalDateTime.now()),
//                new Employees(4, "name4", "name4@mycompany.com",LocalDateTime.now(),LocalDateTime.now()),
//                new Employees(5, "name5", "name5@mycompany.com",LocalDateTime.now(),LocalDateTime.now()),
//                new Employees(6, "name6", "name6@mycompany.com",LocalDateTime.now(),LocalDateTime.now())
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
//    @Test
//    void testFindEmployeeUsingPaging_NOTFOUND(){
//        PageRequest pageRequest = PageRequest.of(0, 5);
//
//
//        when(employeeRepository.findAll(pageRequest)).thenReturn(Page.empty());
//        assertThrows(EmployeeNotFoundException.class,()->employeeService.findEmployeesUsingPaging(0,5));
//
//    }
//
//    @Test
//    void testFindEmployeeUsingPaging_NULL(){
//        PageRequest pageRequest = PageRequest.of(0, 5);
//        InvalidInputException thrown = assertThrows(InvalidInputException.class, () -> {
//                    employeeService.findEmployeesUsingPaging(-1,-1);
//        });
//        assertEquals("input is invalid", thrown.getMessage());
//    }
//
//
//
//    @Test
//    void testUpdateEmployee() {
//        when(employeeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(sampleEmployees));
//        EmployeeDTO result = employeeService.updateEmployees(1L, sampleEmployeeDTO);
//        assertNotNull(result);
//        assertEquals(result,sampleEmployeeDTO);
//    }
//
//    @Test
//    void testUpdateEmployee_NOTFOUND(){
//        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
//        assertThrows(EmployeeNotFoundException.class,()->employeeService.updateEmployees(1L,sampleEmployeeDTO));
//    }
//
//    @Test
//    void testDeleteEmployee() {
//        when(employeeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(sampleEmployees));
//        Optional<Employees> result = employeeService.deleteEmployees(1L);
//        assertNotNull(result);
//        Optional<Employees> optionalEmployees = Optional.of(sampleEmployees);
//       assertEquals(result,optionalEmployees);
//    }
//
//    @Test
//    void testDeleteEmployee_NOTFOUND(){
//        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
//        assertThrows(EmployeeNotFoundException.class,()-> employeeService.deleteEmployees(1L));
//    }

}
