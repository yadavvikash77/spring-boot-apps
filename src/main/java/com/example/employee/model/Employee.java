package com.example.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long Id;

    @Column(name = "firstname", nullable = false)
    @NotBlank(message = "{validation.property.firstname.notblank}")
    /*@Pattern(regexp = "^[a-zA-Z]+$", message = "{validation.property.firstname.pattern}")*/
    private String firstName;

    @Column(name = "lastname", nullable = false)
    @NotBlank(message = "{validation.property.lastname.notblank}")
    /*@Pattern(regexp = "^[a-zA-Z]+$", message = "{validation.property.lastname.pattern}")*/
    private String lastName;

    @Column(name = "email", nullable = false)
    @NotBlank(message = "{validation.property.email.notblank}")
    @Email
    private String email;

    @Column(name = "gender", nullable = false)
    @NotBlank(message = "{validation.property.gender.notblank}")
    private String gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id",referencedColumnName = "id")
    private Address address;
}
