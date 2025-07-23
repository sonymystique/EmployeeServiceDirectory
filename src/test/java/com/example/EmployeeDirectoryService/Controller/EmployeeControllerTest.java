package com.example.EmployeeDirectoryService.Controller;


import com.example.EmployeeDirectoryService.EmployeeDTO.EmployeeDTO;
import com.example.EmployeeDirectoryService.Entity.Employees;
import com.example.EmployeeDirectoryService.Service.EmployeeServiceInterface;
import com.example.EmployeeDirectoryService.Service.Impl.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import static org.junit.jupiter.api.Assertions.*;


import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
   // private static final String ERROR_MAPPING = "/error";
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private EmployeeServiceInterface employeeServiceInterface;

    @InjectMocks
    private EmployeeController employeeController;
    private Employees employees;
    private EmployeeDTO employeeDTO;

    List<Employees>  employees1;
    List<EmployeeDTO>  employeeDTO1;

    Page<Employees> employeesPage;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach()
    void setUp(){

        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();

      employees1 = List.of(new Employees(1,"name1","name1@gmail.com"),
              new Employees(2,"name2","name2@gmail.com"));

      employeeDTO1 = List.of(new EmployeeDTO("name1","name1@gmail.com"),
              new EmployeeDTO("name2","name2@mycompany.com"));

    }

    //test case for normal get request
    @Test
    void getAllEmployeeTest() throws Exception{
        when(employeeServiceInterface.getAllDetails()).thenReturn(employeeDTO1);
        mockMvc.perform(get("/employees/getEmployees"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].fullName").value("name1"))
                .andExpect(jsonPath("$[1].email").value("name2@mycompany.com"));

    }
    // test for get request with filter : domain
    @Test
    void getByFilterTest() throws Exception{
        List<EmployeeDTO> filterdList = Arrays.asList(new EmployeeDTO("filteredname","filteredname@mycompany.com"));
        when(employeeServiceInterface.getFilteredDetails()).thenReturn(filterdList);


        mockMvc.perform(get("/employees/getEmployees/ByFilter"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].fullName").value("filteredname"))
                .andExpect(jsonPath("$[0].email").value("filteredname@mycompany.com"));
    }
    // Test for normal post method
    @Test
    void addEmployeeTest() throws Exception{

        EmployeeDTO postDTO = new EmployeeDTO("name1@mycompany.com","name1");
        Employees postList = new Employees(1,"name1","name1@mycompany.com");
        when(employeeServiceInterface.createEmployee(any(EmployeeDTO.class))).thenReturn((EmployeeDTO) postDTO);
//mock
        ResponseEntity<EmployeeDTO> response = employeeController.addEmployee(postDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(postDTO.getFullName(), response.getBody().getFullName());
        assertEquals(postDTO.getEmail(), response.getBody().getEmail());
    }

    @Test
    void getDomainByQueryTest() throws Exception{
        //Employees employee1 = new Employees(1L,"name1@mycompany.com","name1");
        employees1 = List.of(new Employees(1,"name1","name1@mycompany.com"));

        employeeDTO1 = List.of(new EmployeeDTO("name1","name1@gmail.com"),
                new EmployeeDTO("name2","name2@mycompany.com"));


        //String s = "@mycompany";
        when(employeeServiceInterface.getDomainByQuery(anyString())).thenReturn((List<Employees>) employees1);

        mockMvc.perform(get("/employees/getEmployees/UsingQuery"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].fullName").value("name1"))
                .andExpect(jsonPath("$[0].email").value("name1@mycompany.com"));

    }

    @Test
    void getEmployeeBySortTest() throws Exception{
        employees1 = List.of(new Employees(1,"name1","name1@mycompany.com"),
                new Employees(2,"name2","name2@mycompany.com"),
                new Employees(3,"name3","name3@mycompany.com"),
                new Employees(4,"name4","name4@mycompany.com")
                );

        employeeDTO1 = List.of(new EmployeeDTO("name1","name1@gmail.com"),
                new EmployeeDTO("name2","name2@mycompany.com"));

        when(employeeServiceInterface.findEmployeesUsingSort(anyString())).thenReturn((List<Employees>) employees1);

        mockMvc.perform(get("/employees/getEmployees/BySort/name"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$.[0].fullName").value("name1"))
                .andExpect(jsonPath("$.[1].fullName").value("name2"))
                .andExpect(jsonPath("$.[2].fullName").value("name3"))
                .andExpect(jsonPath("$.[3].fullName").value("name4"))
                .andExpect(jsonPath("$.[0].email").value("name1@mycompany.com"))
                .andExpect(jsonPath("$.[1].email").value("name2@mycompany.com"))
                .andExpect(jsonPath("$.[2].email").value("name3@mycompany.com"))
                .andExpect(jsonPath("$.[3].email").value("name4@mycompany.com"));
    }


    @Test
    void getEmployeeByPagination() throws Exception{
        employees1 = List.of(new Employees(1,"name1","name1@mycompany.com"),
                new Employees(2,"name2","name2@mycompany.com"),
                new Employees(3,"name3","name3@mycompany.com"),
                new Employees(4,"name4","name4@mycompany.com"),
                new Employees(5,"name5","name5@mycompany.com"),
                new Employees(6,"name6","name6@mycompany.com")
        );

//        employeesPage = new PageImpl<>(employees1);
        Pageable pageable = PageRequest.of(0, 5);
        PageImpl page = new PageImpl<>(employees1, pageable, employees1.size());


        when(employeeServiceInterface.findEmployeesUsingPaging(0,5)).thenReturn((Page<Employees>) page);

        mockMvc.perform(get("/employees/getEmployees/ByPagination/0/5"))
                .andExpect(status().isOk());


    }











}

