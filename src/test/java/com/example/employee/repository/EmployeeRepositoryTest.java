package com.example.employee.repository;

import com.example.employee.enumConstant.Gender;
import com.example.employee.model.Address;
import com.example.employee.model.Employee;
import com.example.employee.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    List<Employee> employeeList, employeeList2;

    @BeforeEach
    public void setup() {
        Post post = Post.builder()
                .id(1L)
                .title("Title Test")
                .description("Description Test")
                .build();
        Address address = Address.builder()
                .id(1L)
                .city("London")
                .state("London")
                .street("Downing Street")
                .zip(132456)
                .build();
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Private")
                .lastName("Ryan")
                .email("private.ryan@xbc.com")
                .gender(Gender.MALE)
                .address(address)
                .posts(List.of(post))
                .build();
        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("JP")
                .lastName("Morgan")
                .email("jp.morgan@xbc.com")
                .gender(Gender.MALE)
                .address(address)
                .posts(List.of(post))
                .build();
        employeeList = new ArrayList<Employee>();
        employeeList.add(employee);
        employeeList.add(employee2);
        employeeList2 = employeeRepository.saveAll(employeeList);
    }

    @Test
    void testEmployeeRepository_saveAll_ReturnSavedEmployeeList() {
        assertNotNull(employeeList2);
        assertTrue(employeeList2.size() > 1);
    }

    @Test
    void testEmployeeRepository_findEmployeeById_ReturnEmployee() {
        Employee employee = employeeRepository.findById(employeeList2.get(0).getId()).get();
        assertNotNull(employee);
        assertTrue(employee.getId() > 0);
    }

    @Test
    void testEmployeeRepository_findAll_ReturnEmployeeList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("firstName").ascending());
        List<Employee> employeeList1 = employeeRepository.findAll(pageable).getContent();
        assertNotNull(employeeList1);
        assertEquals(2, employeeList1.size());
        assertTrue(employeeList1.get(0).getFirstName().startsWith("J"));
    }

    @Test
    void testEmployeeRepository_Delete_ReturnEmpty() {
        employeeRepository.deleteById(employeeList2.get(0).getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeList2.get(0).getId());
        assertTrue(employeeOptional.isEmpty());
    }
}