package com.example.employee.service;

import com.example.employee.dto.EmployeeDto;
import com.example.employee.model.Employee;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getAllEmployees(int pageNo, int pageSize, String sortBy);
    void saveAllEmployeeDetails(List<Employee> employees);
    Employee findEmployeeById(Long id);
    void removeEmployeeDetails(long id);
    Employee updateEmployeeDetails(Employee employee, long id);
}
