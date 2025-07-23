package com.example.EmployeeDirectoryService.Repository;

import com.example.EmployeeDirectoryService.Entity.Employees;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class EmployeeRepositoryTest {
    @Mock
    private EmployeeRepository repo;

    @Test
    public void testGetEmployeeByDomain(){
        Employees employees = new Employees(1,"name1","name1@mycompany.com");
        when(repo.getEmployeeWithDomain(anyString())).thenReturn(List.of(employees));
        String domain = "@mycompany.com";
        List<Employees> listEmployees = repo.getEmployeeWithDomain(domain);

        listEmployees.forEach(System.out::println);
    }





}
