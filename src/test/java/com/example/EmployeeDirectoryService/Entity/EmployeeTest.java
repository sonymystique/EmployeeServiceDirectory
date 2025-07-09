package com.example.EmployeeDirectoryService.Entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    @Test
    void testGettersAndSetters(){
        Employees employees = new Employees();

        employees.setId(1L);
        employees.setName("name1");
        employees.setEmployeeEmail("name1@mycompany.com");

        assertEquals(1L,employees.getId());
        assertEquals("name1",employees.getName());
        assertEquals("name1@mycompany.com",employees.getEmployeeEmail());


    }
}
