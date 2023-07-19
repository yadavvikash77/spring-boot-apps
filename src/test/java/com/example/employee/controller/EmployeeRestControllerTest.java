package com.example.employee.controller;

import com.example.employee.dto.EmployeeDto;
import com.example.employee.enumConstant.Gender;
import com.example.employee.model.Address;
import com.example.employee.model.Employee;
import com.example.employee.model.Post;
import com.example.employee.restClient.GetToDoListRestTemplate;
import com.example.employee.service.impl.EmployeeServiceImpl;
import com.example.employee.utilities.EntityModelAssembler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EmployeeRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class EmployeeRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    EmployeeServiceImpl employeeService;
    @MockBean
    EntityModelAssembler entityModelAssembler;
    @MockBean
    GetToDoListRestTemplate getToDoListRestTemplate;
    @Autowired
    ObjectMapper objectMapper;

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
    void testEmployeeRestController_getEmployeeDetails_ReturnEmployeeList() throws Exception {
        when(employeeService.getAllEmployees(anyInt(), anyInt(), anyString())).thenReturn(List.of(employeeDto));
        mockMvc.perform(get("/employees")
                        .param("pageNo", "0")
                        .param("pageSize", "10")
                        .param("sortBy", "firstName")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].firstName", CoreMatchers.is(employeeDto.getFirstName())))
                .andDo(print());
    }

    @Test
    void testEmployeeRestController_getEmployeeById_ReturnEmployee() throws Exception {
        when(employeeService.findEmployeeById(anyLong())).thenReturn(employee);
        EntityModel<Employee> entityModel = EntityModel.of(employee);
        when(entityModelAssembler.toModel(any(Employee.class))).thenReturn(entityModel);
        mockMvc.perform(get("/employees/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())));
    }

    @Test
    void testEmployeeRestController_saveAllEmployees_ReturnSuccessString() throws Exception {
        doNothing().when(employeeService).saveAllEmployeeDetails(List.of(employee));
        String requestBody = objectMapper.writeValueAsString(List.of(employee));
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string("Record Created!!"));
    }

    @Test
    void testEmployeeRestController_updateEmployeeDetails_ReturnUpdatedEmployee() throws Exception {
        when(employeeService.updateEmployeeDetails(any(Employee.class), anyLong())).thenReturn(employee);
        employee.setFirstName("Sam");
        EntityModel<Employee> entityModel = EntityModel.of(employee);
        when(entityModelAssembler.toModel(any(Employee.class))).thenReturn(entityModel);
        String requestBody = objectMapper.writeValueAsString(employee);
        mockMvc.perform(put("/employees/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())));
    }

    @Test
    void testEmployeeRestController_deleteEmployee_ReturnStringMessage() throws Exception {
        doNothing().when(employeeService).removeEmployeeDetails(anyLong());
        mockMvc.perform(delete("/employees/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Record Removed"));
    }
}