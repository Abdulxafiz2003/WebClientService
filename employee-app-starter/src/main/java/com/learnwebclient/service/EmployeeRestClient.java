package com.learnwebclient.service;

import com.learnwebclient.dto.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.learnwebclient.constants.EmployeeConstants.*;

@Slf4j
public class EmployeeRestClient {
    private WebClient webClient;

    public EmployeeRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Employee> retrieveAllEmployees() {
        return webClient.get()
                .uri(GET_ALL_EMPLOYEES_V1)
                .retrieve()
                .bodyToFlux(Employee.class)
                .collectList()
                .block();
    }

    public Employee retrieveEmployeeById(int employeeId) {
        try {
            return webClient.get()
                    .uri(EMPLOYEE_BY_ID_V1, employeeId)
                    .retrieve()
                    .bodyToMono(Employee.class)
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("Error response Code is {} and the response body is {} ", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            log.error("WebClientResponseException in retrieveEmployeeId ", ex);
            throw ex;
        }catch (Exception e){
            log.error("Exception in retrieveEmployeeById ", e);
            throw e;
        }
    }

    public List<Employee> retrieveEmployeeByName(String employeeName){
        try {
            String uri = UriComponentsBuilder.fromUriString(EMPLOYEE_BY_NAME_V1)
                    .queryParam("employee_name", employeeName)
                    .build().toString();

            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToFlux(Employee.class)
                    .collectList()
                    .block();
        }catch (WebClientResponseException ex) {
            log.error("Error response Code is {} and the response body is {} ", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            log.error("WebClientResponseException in retrieveEmployeeByName ", ex);
            throw ex;
        }catch (Exception e){
            log.error("Exception in retrieveEmployeeByName ", e);
            throw e;
        }
    }


    public Employee addNewEmployee(Employee employee){
        try {
            return webClient.post()
                    .uri(ADD_NEW_EMPLOYEE_V1)
                    .syncBody(employee)
                    .retrieve()
                    .bodyToMono(Employee.class)
                    .block();
        }catch (WebClientResponseException ex) {
            log.error("Error response Code is {} and the response body is {} ", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            log.error("WebClientResponseException in addNewEmployee ", ex);
            throw ex;
        }catch (Exception e){
            log.error("Exception in addNewEmployee ", e);
            throw e;
        }
    }

    public Employee updateEmployee(int employeeId, Employee employee){
        try {
            return webClient.put()
                    .uri(EMPLOYEE_BY_ID_V1, employeeId)
                    .syncBody(employee)
                    .retrieve()
                    .bodyToMono(Employee.class)
                    .block();
        }catch (WebClientResponseException ex) {
            log.error("Error response Code is {} and the response body is {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            log.error("WebClientResponseException in updateEmployee", ex);
            throw ex;
        }catch (Exception e){
            log.error("Exception in updateEmployee", e);
            throw e;
        }
    }

    public void deleteEmployee(int employeeId){
        try {
            webClient.delete()
                    .uri(EMPLOYEE_BY_ID_V1, employeeId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }catch (WebClientResponseException ex) {
            log.error("Error response Code is {} and the response body is {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            log.error("WebClientResponseException in deleteEmployee", ex);
            throw ex;
        }catch (Exception e){
            log.error("Exception in deleteEmployee", e);
            throw e;
        }
    }
}
