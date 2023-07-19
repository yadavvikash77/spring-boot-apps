package com.example.employee.controller;

import com.example.employee.dto.EmployeeDto;
import com.example.employee.dto.Post;
import com.example.employee.model.Employee;
import com.example.employee.restClient.GetToDoListRestTemplate;
import com.example.employee.service.EmployeeService;
import com.example.employee.utilities.EntityModelAssembler;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.List;

/**
 * The type Employee rest controller.
 */
@RestController
@RequestMapping("/employees")
@Api(tags = {"Employee Controller"}, value = "Employee Controller")
@RequiredArgsConstructor
@Slf4j
public class EmployeeRestController {

    private final EmployeeService employeeService;
    private final EntityModelAssembler entityModelAssembler;
    private final GetToDoListRestTemplate getToDoListRestTemplate;

    /**
     * Gets employee details.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @param sortBy   the sort by
     * @return the employee details
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeDto>> getEmployeeDetails(@RequestParam(value = "pageNo", required = false, defaultValue = "0") final int pageNo,
                                                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") final int pageSize,
                                                                @RequestParam(value = "sortBy", required = false, defaultValue = "firstName") final String sortBy) {
        List<EmployeeDto> entityModelList = employeeService.getAllEmployees(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(entityModelList);
    }

    /**
     * Gets employee by id.
     *
     * @param id the id
     * @return the employee by id
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Employee>> getEmployeeById(@PathVariable("id") final Long id) {
        Employee employee = employeeService.findEmployeeById(id);
        return ResponseEntity.ok(entityModelAssembler.toModel(employee));
    }

    /**
     * Save all employees response entity.
     *
     * @param employees the employees
     * @return the response entity
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveAllEmployees(@RequestBody Employee[] employees) {
        employeeService.saveAllEmployeeDetails(Arrays.asList(employees));
        return new ResponseEntity<>("Record Created!!", HttpStatus.CREATED);
    }

    /**
     * Update employee details response entity.
     *
     * @param employee the employee
     * @param id       the id
     * @return the response entity
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Employee>> updateEmployeeDetails(@RequestBody Employee employee, @PathVariable("id") final Long id) {
        EntityModel<Employee> entityModel = entityModelAssembler.toModel(employeeService.updateEmployeeDetails(employee, id));
        return ResponseEntity.ok(entityModel);
    }

    /**
     * Delete employee response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") final Long id) {
        employeeService.removeEmployeeDetails(id);
        return ResponseEntity.ok("Record Removed");
    }

    /**
     * Get posts list.
     *
     * @return the list
     */
    @GetMapping("/posts")
    @ApiIgnore
    public List<Post> getPosts() {
        return getToDoListRestTemplate.getPosts();
    }
}
