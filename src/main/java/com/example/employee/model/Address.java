package com.example.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    @Column(name = "address_id")
    private Long id;
    @Column(name = "street")
    private String street;
    @Column(name = "city")
    @NotBlank(message = "{validation.property.city.notblank}")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "zip")
    private int zip;

}
