package com.development.ecommerce_tecnology.entity;

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

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo", nullable = false, length = 255)
    private String correo;

    @Column(name = "contraseña" , nullable = false, length = 255)
    private String contrasenia;

    @Column(name = "fecha_registro")
    private LocalDateTime fecha_registro;

    @NotNull(message = " El rol es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "id_rol" , nullable = false)
    private Rol rol;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public LocalDateTime getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDateTime fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public @NotNull(message = " El rol es obligatorio") Rol getRol() {
        return rol;
    }

    public void setRol(@NotNull(message = " El rol es obligatorio") Rol rol) {
        this.rol = rol;
    }
}
