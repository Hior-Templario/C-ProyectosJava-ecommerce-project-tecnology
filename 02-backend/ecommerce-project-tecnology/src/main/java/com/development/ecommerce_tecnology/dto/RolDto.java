package com.development.ecommerce_tecnology.dto;

import com.development.ecommerce_tecnology.entity.Rol;

public class RolDto {

    Long idRol;
    String nombreRol;

    public RolDto(String nombreRol, Long idRol) {
        this.nombreRol = nombreRol;
        this.idRol = idRol;
    }

    public RolDto(Rol rol) {
        this.nombreRol = rol.getNombreRol();
        this.idRol = rol.getIdRol();
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}
