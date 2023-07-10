package com.example.employee.dto;

import com.example.employee.dto.AddressDto;
import com.example.employee.model.Employee;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * A DTO for the {@link Employee} entity
 */
@Data
public class EmployeeDto implements Serializable {
    private long Id;
    @NotBlank(message = "{validation.property.firstname.notblank}")
    private  String firstName;
    @NotBlank(message = "{validation.property.lastname.notblank}")
    private String lastName;
    @NotBlank(message = "{validation.property.email.notblank}")
    @Email
    private String email;
    @NotBlank(message = "{validation.property.gender.notblank}")
    private String gender;
    private AddressDto address;
}