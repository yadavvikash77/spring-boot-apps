package com.example.employee.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Company {

    private String name;
    private String address;
    private String type;
}
