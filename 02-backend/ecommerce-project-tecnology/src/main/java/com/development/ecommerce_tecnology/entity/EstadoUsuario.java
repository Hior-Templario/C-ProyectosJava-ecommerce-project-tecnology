package com.development.ecommerce_tecnology.entity;

import jakarta.persistence.*;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

@Entity
@Table(name = "estado_usuario")
public class EstadoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstado;

    @Column(name = "nombre_estado" , length = 45, unique = true)
    private String nombreEstado;

    @Column(name = "descripcion")
    private String descripcion;


    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
