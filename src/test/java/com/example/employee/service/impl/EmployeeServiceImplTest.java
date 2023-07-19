package com.example.employee.service.impl;

import com.example.employee.dto.EmployeeDto;
import com.example.employee.enumConstant.Gender;
import com.example.employee.exception.DataNotFoundException;
import com.example.employee.model.Address;
import com.example.employee.model.Employee;
import com.example.employee.model.Post;
import com.example.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    @Mock
    private ModelMapper modelMapper;
    Employee employee;
    EmployeeDto employeeDto;

    @BeforeEach
    public void setup() {
        Address address = Address.builder()
                .id(1L)
                .city("London")
                .state("London")
                .street("Downing Street")
                .zip(132456)
                .build();
        Post post = Post.builder()
                .id(1L)
                .title("Title Test")
                .description("Description Test")
                .build();
        employee = Employee.builder()
                .id(1L)
                .firstName("Private")
                .lastName("Ryan")
                .email("private.ryan@xbc.com")
                .gender(Gender.MALE)
                .address(address)
                .posts(List.of(post))
                .build();
        employeeDto = EmployeeDto.builder()
                .Id(1L)
                .firstName("John")
                .lastName("kel")
                .city("London")
                .build();
    }

    @Test
    void testEmployeeService_getAllEmployees_returnEmployeeList() {
        Page<Employee> employeePage = Mockito.mock(Page.class);
        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(employeePage);
        when(employeePage.getContent()).thenReturn(List.of(employee));
        when(modelMapper.map(any(), any())).thenReturn(employeeDto);
        List<EmployeeDto> employees = employeeService.getAllEmployees(0, 10, "firstName");
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
        assertEquals("John", employees.get(0).getFirstName());
    }

    @Test
    void testEmployeeService_saveAllEmployeeDetails_ReturnEmployeeList() {
        when(employeeRepository.saveAll(anyList())).thenReturn(List.of(employee));
        employeeService.saveAllEmployeeDetails(List.of(employee));
        verify(employeeRepository, timeout(1)).saveAll(List.of(employee));
    }

    @Test
    void testEmployeeService_findEmployeeById_ReturnEmployee() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        Employee employee = employeeService.findEmployeeById(1L);
        assertNotNull(employee);
        assertTrue(employee.getId() > 0);
    }

    @Test
    void testEmployeeService_findEmployeeById_ThrowsException() {
        when(employeeRepository.findById(anyLong())).thenThrow(DataNotFoundException.class);
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            employeeService.findEmployeeById(65L);
        });
        assertNotNull(exception);
    }

    @Test
    void testEmployeeService_removeEmployeeDetails_DeleteEmployee() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).deleteById(1L);
        employeeService.removeEmployeeDetails(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEmployeeService_removeEmployeeDetails_ThrowsException() {
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            employeeService.removeEmployeeDetails(1L);
        });
        assertNotNull(exception);
    }

    @Test
    void testEmployeeService_updateEmployeeDetails_ReturnEmployee() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        employee.setEmail("abc@gmail.com");
        employee.setLastName("mez");
        Employee employee1 = employeeService.updateEmployeeDetails(employee, 1L);
        assertEquals("abc@gmail.com", employee1.getEmail());
        assertEquals("mez", employee1.getLastName());
    }

    @Test
    void testEmployeeService_updateEmployeeDetails_ThrowsException() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        employee.setEmail("abc@gmail.com");
        employee.setLastName("mez");
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            employeeService.updateEmployeeDetails(employee, 1L);
        });
        assertNotNull(exception);
    }
}