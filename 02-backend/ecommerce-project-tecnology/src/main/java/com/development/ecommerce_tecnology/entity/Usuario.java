package com.development.ecommerce_tecnology.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre_usuario", nullable = false, length = 100, unique = true)
    private String nombreUsuario;

    @Column(name = "correo", nullable = false, length = 255, unique = true)
    private String correo;

    @Column(name = "contraseña" , nullable = false, length = 255)
    private String contrasenia;

    @Column(name = "fecha_registro")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "id_rol" , nullable = false)
    @NotNull(message = " El rol es obligatorio")
    @JsonManagedReference
    private Rol rol;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "id_persona" , unique = true)
//    @NotNull(message = "Datos de la persona son obligatorios")
    @JsonManagedReference
    private Persona persona;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "id_estado" , nullable = false)
    @NotNull(message = " El estado de usuario es obligatorio")
    @JsonManagedReference
    private EstadoUsuario estadoUsuario;


    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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

    public @NotNull(message = " El rol es obligatorio") Rol getRol() {
        return rol;
    }

    public void setRol(@NotNull(message = " El rol es obligatorio") Rol rol) {
        this.rol = rol;
    }

    public  Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @PrePersist
    protected void ocCreate(){
        this.fechaRegistro = LocalDateTime.now();
    }


    public @NotNull(message = " El estado de usuario es obligatorio") EstadoUsuario getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(@NotNull(message = " El estado de usuario es obligatorio") EstadoUsuario estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }
}

//✔ Cuando se recibe info del frontend (crear usuario):
// Frontend ----> UsuarioCrearDto ----> construirUsuarioDesdeDto(...) ----> Usuario ----> Guardar en BD

// ✔ Cuando se envía info al frontend:
//Usuario (desde la BD) + Imagen ---> UsuarioMapper.mappearUsuarioDto(...) ---> UsuarioDto ---> Frontend

