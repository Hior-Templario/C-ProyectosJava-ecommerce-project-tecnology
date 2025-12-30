package com.development.ecommerce_tecnology.dto;

import java.time.LocalDateTime;

public class UsuarioActualizarDto {

    // Atributos del usuario
    private String nombreUsuario;
    private String correo;
    private String contrasenia;

    // Datos relacionados con el rol del usuario
    private Long  idRol;
    private String nombreRol;

    // Datos relacionados con el estado del usuario
    private Long  idEstado;
    private String nombreEstado;
//    private String descripcionEstado;


    public String getNombreRol() {
        return nombreRol;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
}
