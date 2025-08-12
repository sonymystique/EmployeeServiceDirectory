package com.example.EmployeeDirectoryService.controller;


import com.example.EmployeeDirectoryService.employeeDto.EmployeeDTO;
import com.example.EmployeeDirectoryService.entity.Employees;
import com.example.EmployeeDirectoryService.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
//    private static final String ERROR_MAPPING = "/error";
//    @Autowired
//    private MockMvc mockMvc;
//    @Mock
//    private EmployeeService employeeService;
//
//    @InjectMocks
//    private EmployeeController employeeController;
//    private Employees employees;
//    private EmployeeDTO employeeDTO;
//
//    List<Employees> employeesList;
//    List<EmployeeDTO> employeeDTOList;
//
//    Page<Employees> employeesPage;
//    private ObjectMapper objectMapper;
//
//
//
//
//
//
//    @BeforeEach()
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        employeesList = List.of(new Employees(1, "name1", "name1@gmail.com", LocalDateTime.now(), LocalDateTime.now()),
//                new Employees(2, "name2", "name2@gmail.com", LocalDateTime.now(), LocalDateTime.now()));
//
//        employeeDTOList = List.of(new EmployeeDTO("name1", "name1@gmail.com", LocalDateTime.now(), LocalDateTime.now()),
//                new EmployeeDTO("name2", "name2@mycompany.com", LocalDateTime.now(), LocalDateTime.now()));
//
//    }
//
//
//    //test case for get request
//    @Test
//    void getAllEmployeeTest() throws Exception {
//        when(employeeService.getAllDetails()).thenReturn(employeeDTOList);
//        mockMvc.perform(get("/ems/employees"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].fullName").value("name1"))
//                .andExpect(jsonPath("$[1].email").value("name2@mycompany.com"));
//    }
//
//    @Test
//    void getEmployeeByPagination() throws Exception {
//        employeesList = List.of(
//                new Employees(1, "name1", "name1@mycompany.com", LocalDateTime.now(), LocalDateTime.now()),
//                new Employees(2, "name2", "name2@mycompany.com", LocalDateTime.now(), LocalDateTime.now()),
//                new Employees(3, "name3", "name3@mycompany.com", LocalDateTime.now(), LocalDateTime.now()),
//                new Employees(4, "name4", "name4@mycompany.com", LocalDateTime.now(), LocalDateTime.now()),
//                new Employees(5, "name5", "name5@mycompany.com", LocalDateTime.now(), LocalDateTime.now())
//        );
//        Pageable pageable = PageRequest.of(0, 5);
//        PageImpl page = new PageImpl<>(employeesList, pageable, employeesList.size());
//
//log.info("employees",employeesList);
//        when(employeeService.findEmployeesUsingPaging(0,5)).thenReturn(page);
//log.info("Page",page);
//        mockMvc.perform(get("/ems/employees?offset=0&page=5")
////                .param("page", String.valueOf(0))
////                .param("size", String.valueOf(5)))
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].name").value("name1"))
//                .andExpect(jsonPath("$.content[1].name").value("name2"))
//                .andExpect(jsonPath("$.content[2].name").value("name3"))
//                .andExpect(jsonPath("$.content[3].name").value("name4"))
//                .andExpect(jsonPath("$.content[4].name").value("name5"))
//                .andExpect(jsonPath("$.content[0].employeeEmail").value("name1@mycompany.com"))
//                .andExpect(jsonPath("$.content[1].employeeEmail").value("name2@mycompany.com"))
//                .andExpect(jsonPath("$.content[2].employeeEmail").value("name3@mycompany.com"))
//                .andExpect(jsonPath("$.content[3].employeeEmail").value("name4@mycompany.com"))
//                .andExpect(jsonPath("$.content[4].employeeEmail").value("name5@mycompany.com"))
//                ;
//
//    }
//
//
////     test for get request with filter : domain
//    @Test
//    void getByFilterTest() throws Exception{
//        List<EmployeeDTO> filterdList = Arrays.asList(new EmployeeDTO("filteredname","filteredname@mycompany.com", LocalDateTime.now(), LocalDateTime.now()));
//        when(employeeService.getFilteredDetails()).thenReturn(filterdList);
//        mockMvc.perform(get("/ems/employees/filterByDomain"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(1))
//                .andExpect(jsonPath("$[0].fullName").value("filteredname"))
//                .andExpect(jsonPath("$[0].email").value("filteredname@mycompany.com"));
//      }
//    // Test for normal post method
//    @Test
//    void addEmployeeTest() throws Exception{
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        EmployeeDTO postDTO = new EmployeeDTO("name1","name1@mycompany.com",LocalDateTime.now(), LocalDateTime.now());
//        Employees postList = new Employees(1,"name1","name1@mycompany.com",LocalDateTime.now(), LocalDateTime.now());
//        when(employeeService.createEmployee(any(EmployeeDTO.class))).thenReturn(postDTO);
//        mockMvc.perform(post("/ems/employee")
//                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
//                .content(mapper.writeValueAsString(postDTO)))
//                .andExpect((status().isCreated()))
//                .andExpect(jsonPath("$.fullName").value("name1"))
//                .andExpect(jsonPath("$.email").value("name1@mycompany.com"));
//
//      }
//
//    @Test
//    void updateEmployeeTest() throws Exception{
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        EmployeeDTO postDTO = new EmployeeDTO("name1","name1@mycompany.com",LocalDateTime.now(), LocalDateTime.now());
//        Employees postList = new Employees(1,"name1","name1@mycompany.com",LocalDateTime.now(), LocalDateTime.now());
//        when(employeeService.updateEmployees(anyLong(),any(EmployeeDTO.class))).thenReturn(postDTO);
//        mockMvc.perform(put("/ems/employee/1")
//                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
//                        .content(mapper.writeValueAsString(postDTO)))
//                .andExpect((status().isOk()))
//                .andExpect(jsonPath("$.fullName").value("name1"))
//                .andExpect(jsonPath("$.email").value("name1@mycompany.com"));
//
//    }
//
//    @Test
//    void deleteEmployeeTest() throws Exception{
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        EmployeeDTO postDTO = new EmployeeDTO("name1","name1@mycompany.com",LocalDateTime.now(), LocalDateTime.now());
//        Employees postList = new Employees(1,"name1","name1@mycompany.com",LocalDateTime.now(), LocalDateTime.now());
//        when(employeeService.deleteEmployees(anyLong())).thenReturn(Optional.of(postList));
//        mockMvc.perform(delete("/ems/employee/1")
//                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
//                        .content(mapper.writeValueAsString(postDTO)))
//                .andExpect((status().isOk()))
//                .andExpect(jsonPath("$.name").value("name1"))
//                .andExpect(jsonPath("$.employeeEmail").value("name1@mycompany.com"));
//
//    }
//
//
//



}

