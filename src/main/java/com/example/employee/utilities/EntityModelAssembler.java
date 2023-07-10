package com.example.employee.utilities;

import com.example.employee.controller.EmployeeRestController;
import com.example.employee.model.Employee;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EntityModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {
    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        return EntityModel.of(employee,linkTo(methodOn(EmployeeRestController.class).getEmployeeById(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeRestController.class).getEmployeeDetails()).withRel("employees"));
    }
}
