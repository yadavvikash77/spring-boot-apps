package com.example.employee.service.impl;

import com.example.employee.exception.DataNotFoundException;
import com.example.employee.model.Employee;
import com.example.employee.properties.PropertySourceConfig;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "emp")
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private PropertySourceConfig propertySourceConfig;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PropertySourceConfig propertySourceConfig) {
        this.employeeRepository = employeeRepository;
        this.propertySourceConfig = propertySourceConfig;
    }

    @Override
    public List<Employee> getAllEmployees() {
        Pageable pageable = PageRequest.of(propertySourceConfig.getPageNo(),propertySourceConfig.getPageSize(), Sort.by(propertySourceConfig.getPropertyName()).ascending());
        return employeeRepository.findAll(pageable).getContent();
    }

    @Override
    public Page<Employee> getAllEmployeesPaginated(int pageNo, int pageSize) {
        return employeeRepository.findAll(PageRequest.of(pageNo,pageSize,Sort.by(propertySourceConfig.getPropertyName()).ascending()));
    }

    @Override
    public Employee saveEmployeeDetails(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void saveAllEmployeeDetails(List<Employee> employees) {
       employeeRepository.saveAll( employees);
    }

    @Override
    public Employee findEmployeeById(long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new DataNotFoundException("Employee Not Found For Given ID :: "+id));
        return employee;
    }

    @Override
    public void removeEmployeeDetails(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> findEmployeeByFirstName(String firstName) {
        List<Employee> employeeList = employeeRepository.findByFirstNameIgnoreCase(firstName).orElseThrow(()->new DataNotFoundException("Employee Not Found For Given Firstname :: "+firstName));
        return employeeList;
    }
    @Override
    public long getEmployeeCount(){
        return employeeRepository.count();
    }

    @Override
    public Employee updateEmployeeDetails(Employee employee, long id) {
        Employee employee1 = employeeRepository.findById(id).map(emp->{
            emp.setFirstName(employee.getFirstName());
            emp.setLastName(employee.getLastName());
            emp.setAddress(employee.getAddress());
            emp.setEmail(employee.getEmail());
            emp.setGender(employee.getGender());
            return employeeRepository.save(emp);
        }).orElseGet(()->{
            employee.setId(id);
            return employeeRepository.save(employee);}
        );
        return employee1;
    }
}
