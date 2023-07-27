package com.example.employee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comments extends ParentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comments_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToMany(mappedBy = "comments")
    private Set<Post> posts = new HashSet<>();
}
