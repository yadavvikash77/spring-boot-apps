package com.example.employee.service;

import com.example.employee.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Page<Employee> getAllEmployeesPaginated(int pageNo, int pageSize);
    Employee saveEmployeeDetails(Employee employee);
    void saveAllEmployeeDetails(List<Employee> employees);
    Employee findEmployeeById(long id);
    void removeEmployeeDetails(long id);
    List<Employee> findEmployeeByFirstName(String firstName);
    long getEmployeeCount();
    Employee updateEmployeeDetails(Employee employee, long id);
}
