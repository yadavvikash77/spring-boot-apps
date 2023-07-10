package com.example.employee.controller;

import com.example.employee.dto.Post;
import com.example.employee.model.Employee;
import com.example.employee.restClient.GetToDoListRestTemplate;
import com.example.employee.service.EmployeeService;
import com.example.employee.utilities.EntityModelAssembler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/rest/v1")
@Api(tags = {"Employee Controller"}, value = "Employee Controller")
public class EmployeeRestController {

    private EmployeeService employeeService;
    private CacheManager cacheManager;
    private EntityModelAssembler entityModelAssembler;

    private GetToDoListRestTemplate getToDoListRestTemplate;

    public EmployeeRestController(@Qualifier("emp") EmployeeService employeeService, CacheManager cacheManager, EntityModelAssembler entityModelAssembler, GetToDoListRestTemplate getToDoListRestTemplate) {
        this.employeeService = employeeService;
        this.cacheManager = cacheManager;
        this.entityModelAssembler = entityModelAssembler;
        this.getToDoListRestTemplate = getToDoListRestTemplate;
    }

    @GetMapping(value = "/allEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable("employeeCache")
    public ResponseEntity<CollectionModel<EntityModel<Employee>>> getEmployeeDetails(){
        cacheManager.getCacheNames().stream().forEach(cache-> System.out.println("********* "+cache));
        List<EntityModel<Employee>> entityModelList = employeeService.getAllEmployees().stream().map(entityModelAssembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(entityModelList,linkTo(methodOn(EmployeeRestController.class).getEmployeeDetails()).withSelfRel()));
    }

    @GetMapping(value = "/getEmployeeById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Employee>> getEmployeeById(@PathVariable("id") long id){
        Employee employee = employeeService.findEmployeeById(id);
        return ResponseEntity.ok(entityModelAssembler.toModel(employee));
    }

    @GetMapping(value = "/findEmployeeByFirstName/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Employee>>> findEmployeeByFirstName(@PathVariable("firstName") String firstName){
        List<EntityModel<Employee>> entityModelList = employeeService.findEmployeeByFirstName(firstName).stream().map(entityModelAssembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(entityModelList,linkTo(methodOn(EmployeeRestController.class).getEmployeeDetails()).withSelfRel()));
    }

    @PostMapping(value = "/saveEmployeeDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Employee>> saveEmployeeDetails(@RequestBody Employee employee){
        EntityModel<Employee> employeeEntityModel = entityModelAssembler.toModel(employeeService.saveEmployeeDetails(employee));
        return ResponseEntity.created(employeeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(employeeEntityModel);
    }

    @PostMapping(value = "/saveEmployeeDetailsJsonRequestBody", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "",hidden = true)
    public ResponseEntity<String> saveEmployeeDetailsJsonRequestBody(@RequestBody Employee employee){
        employeeService.saveEmployeeDetails(employee);
        return new ResponseEntity<>("Record Created!!",HttpStatus.CREATED);
    }

    @PostMapping(value = "/saveAllEmployees", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveAllEmployees(@RequestBody Employee[] employees){
        employeeService.saveAllEmployeeDetails(Arrays.asList(employees));
        return new ResponseEntity<>("Record Created!!",HttpStatus.CREATED);
    }

    @PutMapping(value = "/updateEmployee/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Employee>> updateEmployeeDetails(@RequestBody Employee employee, @PathVariable("id") long id){
        EntityModel<Employee> entityModel = entityModelAssembler.toModel(employeeService.updateEmployeeDetails(employee,id));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping(value = "/deleteEmployee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") long id){
        employeeService.removeEmployeeDetails(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts")
    public List<Post> getPosts(){
        List<Post> postList = getToDoListRestTemplate.getPosts();
        return postList;
    }
}
