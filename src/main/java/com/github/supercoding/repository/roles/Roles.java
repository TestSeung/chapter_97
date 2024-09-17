package com.github.supercoding.repository.roles;

import jakarta.persistence.*;

@Entity
public class Roles {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "name")
    private String name;
}
