package com.example.EmployeeDirectoryService.Service;

import com.example.EmployeeDirectoryService.EmployeeDTO.EmployeeDTO;
import com.example.EmployeeDirectoryService.EmployeeDTO.EmployeeDTOTest;
import com.example.EmployeeDirectoryService.Entity.Employees;
import com.example.EmployeeDirectoryService.Mapper.EmployeeMapper;
import com.example.EmployeeDirectoryService.Repository.EmployeeRepository;
import com.example.EmployeeDirectoryService.Service.Impl.EmployeeService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {


    private EmployeeMapper employeeMapper = EmployeeMapper.instance;

@Mock
private EmployeeRepository employeeRepository;


    private EmployeeService employeeService;

    private Employees sampleEmployees;
    private EmployeeDTO sampleEmployeeDTO;

    @BeforeEach()
    void setUp(){
        employeeService = new EmployeeService();
        ReflectionTestUtils.setField(employeeService,"employeeRepository",employeeRepository);
        sampleEmployees = new Employees(1L,"name1","name1@gmail.com");
        sampleEmployeeDTO=new EmployeeDTO("name2@gmail.com","name2");
    }

    @Test
    void AddEmployeeTest() {
        Mockito.when(employeeRepository.save(Mockito.any(Employees.class))).thenReturn(sampleEmployees);


        EmployeeDTO result = employeeService.createEmployee(sampleEmployeeDTO);

        assertNotNull(result);
        assertEquals("name1",result.getFullName());
        assertEquals("name1@gmail.com",result.getEmail());

    }

    @Test
    void testGetAllDetails() {
        List<Employees> employees = List.of(new Employees(2L, "name2", "name2@gmail.com"), new Employees(1L, "name1", "name1@gmail.com"));
        List<EmployeeDTO> employeeDTO = List.of(new EmployeeDTO("name2@gmail.com", "name2"), new EmployeeDTO("name1@gmail.com", "name1"));

        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeDTO> employeeDTOS = employeeRepository.findAll().stream()
                .map(EmployeeMapper.instance::toDTO)
                .toList();

        //System.out.println(employeeDTOS);
        assertNotNull(employeeDTOS);
        assertEquals(employeeDTOS.size(), 2);

        //assertThat(employeeDTOS).containsExactlyInAnyOrderElementsOf(employeeDTO);

        //assertEquals(1,employeeDTOS);
    }

    @Test
    void testGetFilteredDetails(){
            List<Employees> employees = List.of(new Employees(2L,"name2","name2@mycompany.com"),new Employees(1L,"name1","name1@gmail.com"));
            List<EmployeeDTO> employeeDTO = List.of(new EmployeeDTO("name2@mycompany.com","name2"));

            Mockito.when(employeeRepository.findAll()).thenReturn(employees);

            List<EmployeeDTO> employeeDTOS = employeeRepository.findAll().stream()
                    .filter(e -> e.getEmployeeEmail()!= null &&
                            e.getEmployeeEmail().contains("@mycompany.com"))
                    .map(EmployeeMapper.instance::toDTO)
               .toList();

            assertNotNull(employeeDTOS);
//            System.out.println(employeeDTOS);
//            System.out.println(employeeDTO);
            //assertEquals(employeeDTOS,employeeDTO);
            assertEquals(employeeDTOS.size(),1);
            assertEquals(employeeDTOS.getFirst().getEmail(),"name2@mycompany.com");


       }
}
