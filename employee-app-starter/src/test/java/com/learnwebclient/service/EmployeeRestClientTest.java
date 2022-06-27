package com.learnwebclient.service;

import com.learnwebclient.dto.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeRestClientTest {
    private static final String baseUrl = "http://localhost:8081/employeeservice";

    private final WebClient webClient = WebClient.create(baseUrl);

    EmployeeRestClient employeeRestClient = new EmployeeRestClient(webClient);

    @Test
    void retrieveAllEmployees(){
        List<Employee> employeeList = employeeRestClient.retrieveAllEmployees();
        System.out.println("employeeList: " + employeeList);
        assertTrue(employeeList.size()>0);
    }

    @Test
    void retrieveEmployeeById(){
        int employeeId = 1;
        Employee employee = employeeRestClient.retrieveEmployeeById(employeeId);
        System.out.println(employee);
        assertEquals("Chris", employee.getFirstName());
    }

    @Test
    void retrieveEmployeeById_not_Found(){
        int employeeId = 10;
//        Employee employee = employeeRestClient.retrieveEmployeeById(employeeId);
//        assertEquals("Chris", employee.getFirstName());
        Assertions.assertThrows(WebClientResponseException.class, ()-> employeeRestClient.retrieveEmployeeById(employeeId));
    }


    @Test
    void retrieveEmployeeByName(){
        String name = "Adam";
        List<Employee> employees = employeeRestClient.retrieveEmployeeByName(name);
        System.out.println(employees);
        assertTrue(employees.size()> 0);
    }

    @Test
    void retrieveEmployeeByName_not_found(){
        String name = "Ronaldo";
//        List<Employee> employees = employeeRestClient.retrieveEmployeeByName(name);
//        assertTrue(employees.size()> 0);
          Assertions.assertThrows(WebClientResponseException.class, () -> employeeRestClient.retrieveEmployeeByName(name));
    }

    @Test
    void addNewEmployee(){
        Employee employee = new Employee(null, 24, "Shoxruh", "Tawpulatov", "male", "Developer");
        Employee employee1 = employeeRestClient.addNewEmployee(employee);
        System.out.println("employee:" + employee1);
        assertNotNull(employee1.getId());
    }

    @Test
    void addNewEmployee_BadRequest(){
        Employee employee = new Employee(null, 24, null, "Tawpulatov", "male", "Developer");
        Assertions.assertThrows(WebClientResponseException.class, () -> employeeRestClient.addNewEmployee(employee));
    }


    @Test
    void updateEmployee(){
        Employee employee = new Employee(null, 54, "Adam", "Sandler", "male", null);
        //Employee updatedEmployee =
                employeeRestClient.updateEmployee(2, employee);

//        assertEquals("Adam1", updatedEmployee.getFirstName());
//        assertEquals("Sandler1", updatedEmployee.getLastName());
    }

    @Test
    void deleteEmployee(){
        employeeRestClient.deleteEmployee(5);
    }
}
