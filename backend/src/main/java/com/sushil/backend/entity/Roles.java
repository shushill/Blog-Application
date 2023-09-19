package com.sushil.backend.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "roles")
public class Roles {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
