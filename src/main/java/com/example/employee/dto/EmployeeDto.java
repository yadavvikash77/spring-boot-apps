package com.example.employee.dto;

import com.example.employee.dto.AddressDto;
import com.example.employee.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * A DTO for the {@link Employee} entity
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto implements Serializable {
    private long Id;
    private  String firstName;
    private String lastName;
    private String city;
}