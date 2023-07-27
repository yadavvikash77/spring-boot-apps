package com.example.employee.model;

import com.example.employee.entityListener.EmployeeEntityListener;
import com.example.employee.enumConstant.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Employee")
@EntityListeners(EmployeeEntityListener.class)
public class Employee extends ParentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "emp_id")
    private Long id;

    @Column(name = "firstname", nullable = false)
    @NotBlank(message = "{validation.property.firstname.notblank}")
    private String firstName;

    @Column(name = "lastname", nullable = false)
    @NotBlank(message = "{validation.property.lastname.notblank}")
    private String lastName;

    @Column(name = "email", nullable = false)
    @NotBlank(message = "{validation.property.email.notblank}")
    @Email
    private String email;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_address_id", referencedColumnName = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_emp_id", referencedColumnName = "emp_id")
    private List<Post> posts;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "name", column = @Column(name = "company_name", nullable = false)),
            @AttributeOverride(name = "address", column = @Column(name = "company_address", nullable = false)),
            @AttributeOverride(name = "type", column = @Column(name = "company_type", nullable = false))
    })
    private Company company;
}
