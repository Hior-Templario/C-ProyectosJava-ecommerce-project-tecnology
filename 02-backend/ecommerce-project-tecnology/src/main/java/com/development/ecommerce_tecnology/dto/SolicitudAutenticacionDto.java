package com.development.ecommerce_tecnology.dto;

// DTO: Data Transfer Object
// Esta clase se utiliza para ** recibir datos de autenticación ** desde el cliente
// Contiene únicamente los campos necesarios para el login: nombre de usuario y contraseña
public class SolicitudAutenticacionDto {

    // Campo que almacenará el nombre de usuario enviado por el cliente
    private String nombreUsuario;

    // Campo que almacenará la contraseña de usuario enviado por el cliente
    private String contrasenia;

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
}
