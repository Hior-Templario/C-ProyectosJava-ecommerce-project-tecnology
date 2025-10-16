package com.development.ecommerce_tecnology.dto;

import com.development.ecommerce_tecnology.entity.EstadoUsuario;

public class EstadoUsuarioDto {

    Long idEstado;
    String nombreEstado;
    String Descripcion;

    public EstadoUsuarioDto(EstadoUsuario estadoUsuario) {
        this.idEstado = estadoUsuario.getIdEstado();
        this.Descripcion = estadoUsuario.getDescripcion();
        this.nombreEstado = estadoUsuario.getNombreEstado();
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
}
