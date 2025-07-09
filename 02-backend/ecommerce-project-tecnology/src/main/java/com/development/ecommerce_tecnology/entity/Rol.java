package com.development.ecommerce_tecnology.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRol;

    @Column (name = "nombre")
    private String nombre;


}
