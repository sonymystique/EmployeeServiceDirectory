package com.example.EmployeeDirectoryService.Controller;


import com.example.EmployeeDirectoryService.EmployeeDTO.EmployeeDTO;
import com.example.EmployeeDirectoryService.Entity.Employees;
import com.example.EmployeeDirectoryService.Service.EmployeeServiceInterface;
import com.example.EmployeeDirectoryService.Service.Impl.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

import static org.mockito.ArgumentMatchers.any;
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

    private ObjectMapper objectMapper = new ObjectMapper();







    @BeforeEach()
    void setUp(){

        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();

      employees1 = List.of(new Employees(1L,"name1","name1@gmail.com"),
              new Employees(2L,"name2","name2@gmail.com"));

      employeeDTO1 = List.of(new EmployeeDTO("name1@gmail.com","name1"),
              new EmployeeDTO("name2@mycompany.com","name2"));

    }


    @Test
    void getAllEmployeeTest() throws Exception{
        when(employeeServiceInterface.getAllDetails()).thenReturn(employeeDTO1);
        mockMvc.perform(get("/Employees/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].fullName").value("name1"))
                .andExpect(jsonPath("$[1].email").value("name2@mycompany.com"));

    }

    @Test
    void getByFilterTest() throws Exception{
        List<EmployeeDTO> filterdList = Arrays.asList(new EmployeeDTO("filteredname@mycompany.com","filteredname"));
        when(employeeServiceInterface.getFilteredDetails()).thenReturn(filterdList);

        mockMvc.perform(get("/Employees/getByFilter"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].fullName").value("filteredname"))
                .andExpect(jsonPath("$[0].email").value("filteredname@mycompany.com"));
    }

    @Test
    void addEmployeeTest() throws Exception{

        EmployeeDTO postDTO = new EmployeeDTO("name1@mycompany.com","name1");
        List<Employees> postList = List.of(new Employees(1L,"name1","name1@mycompany.com"));
        when(employeeServiceInterface.createEmployee(any(EmployeeDTO.class))).thenReturn((EmployeeDTO) postDTO);

//        mockMvc.perform(post("/Employees/addEmployeeDetails"))
//                .contentType(MediaType.APPLICATION_JSON)
//                .content()


        ResponseEntity<EmployeeDTO> response = employeeController.addEmployee(postDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());


    }



}

