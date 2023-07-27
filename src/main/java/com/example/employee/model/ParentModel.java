package com.example.employee.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class ParentModel {

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdOn;

    @Column(name = "updated_on", nullable = false)
    @UpdateTimestamp
    private Date updatedOn;
}
