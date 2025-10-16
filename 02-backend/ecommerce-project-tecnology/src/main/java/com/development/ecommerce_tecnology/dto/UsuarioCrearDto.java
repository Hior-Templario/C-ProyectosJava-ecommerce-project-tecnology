package com.development.ecommerce_tecnology.dto;

import java.time.LocalDateTime;

public class UsuarioCrearDto {

    // Atributos del usuario
    private Long idUsuario;
    private String nombreUsuario;
    private String correo;
    private String contrasenia;
    private LocalDateTime fechaRegistro;

    // Datos relacionados con el rol del usuario
    private Long  idRol;
    private String nombreRol;

    // Datos relacionados con el estado del usuario
    private Long  idEstado;
    private String nombreEstado;
    private String descripcionEstado;

    // Imagne asociada al Usuario
    private ImagenCrearDto imagenUsuario;


    // Métodos getter y setter para acceder y modificar atributos

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public ImagenCrearDto getImagenUsuario() {
        return imagenUsuario;
    }

    public void setImagenUsuario(ImagenCrearDto imagenUsuario) {
        this.imagenUsuario = imagenUsuario;
    }
}
