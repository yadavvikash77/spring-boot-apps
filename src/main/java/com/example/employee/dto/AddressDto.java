package com.example.employee.dto;

import java.io.Serializable;

import com.example.employee.model.Address;

import lombok.Data;

/**
 * A DTO for the {@link Address} entity
 */
@Data
public class AddressDto implements Serializable {
	private Long id;
	private String street;
	private String city;
	private String state;
	private int zip;
}