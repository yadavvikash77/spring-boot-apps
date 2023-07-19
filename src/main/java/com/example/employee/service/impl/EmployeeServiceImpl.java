package com.example.employee.service.impl;

import com.example.employee.dto.EmployeeDto;
import com.example.employee.exception.DataNotFoundException;
import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service(value = "emp")
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<EmployeeDto> getAllEmployees(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        List<Employee> employeeList = employeeRepository.findAll(pageable).getContent();
        return employeeList.stream().map(employee -> modelMapper.map(employee, EmployeeDto.class)).collect(Collectors.toList());
    }

    @Override
    public void saveAllEmployeeDetails(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    @Override
    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Employee Not Found For Given ID :: " + id));
    }

    @Override
    public void removeEmployeeDetails(long id) {
        employeeRepository.findById(id).ifPresentOrElse(employee -> employeeRepository.deleteById(id), () -> {
            throw new DataNotFoundException("Employee Not Found For Given ID :: " + id);
        });
    }

    @Override
    public Employee updateEmployeeDetails(Employee employee, long id) {
        return employeeRepository.findById(id).map(emp -> {
            emp.setFirstName(employee.getFirstName());
            emp.setLastName(employee.getLastName());
            emp.setGender(employee.getGender());
            emp.setEmail(employee.getEmail());
            emp.setAddress(employee.getAddress());
            emp.setPosts(employee.getPosts());
            return employeeRepository.save(emp);
        }).orElseThrow(() -> new DataNotFoundException("Employee Not Found For Given ID :: " + id));
    }
}
