package com.development.ecommerce_tecnology.dto;

import com.development.ecommerce_tecnology.entity.EstadoUsuario;
import com.development.ecommerce_tecnology.entity.Rol;
import com.development.ecommerce_tecnology.entity.Usuario;

import java.time.LocalDateTime;

public class UsuarioDto {

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


    // Imagnes asociada al Usuario
    private ImagenDto imagenUsuario;

    public UsuarioDto() {

    }

    // Constructor que inicializa todos los datos desde una entidad usuario y sus lista de imágenes
    public UsuarioDto(Usuario usuario, ImagenDto imagenUsuario) {

        this.idUsuario = usuario.getIdUsuario();
        this.nombreUsuario = usuario.getNombreUsuario();
        this.correo = usuario.getCorreo();
        this.contrasenia = usuario.getContrasenia();
        this.fechaRegistro = usuario.getFechaRegistro();

        // Extrae datos de rol si existe
        Rol rol = usuario.getRol();
        if (rol!= null){
            this.idRol = rol.getIdRol() ;
            this.nombreRol = rol.getNombreRol();
        }

        // Extrae datos del estado si existe
        EstadoUsuario estadoUsuario = usuario.getEstadoUsuario();
        if (estadoUsuario!= null){
            this.idEstado = estadoUsuario.getIdEstado();
            this.nombreEstado = estadoUsuario.getNombreEstado();
            this.descripcionEstado = estadoUsuario.getDescripcion();
        }


        // Asigna imagenes asociada
        this.imagenUsuario = imagenUsuario;

    }


    // Meétodos getter y setter para acceder y modificar atributos
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

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public ImagenDto getImagenUsuario() {
        return imagenUsuario;
    }

    public void setImagenUsuario(ImagenDto imagenUsuario) {
        this.imagenUsuario = imagenUsuario;
    }

}
