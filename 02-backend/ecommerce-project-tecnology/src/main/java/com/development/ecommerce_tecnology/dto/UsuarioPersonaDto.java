package com.development.ecommerce_tecnology.dto;

import com.development.ecommerce_tecnology.entity.EstadoUsuario;
import com.development.ecommerce_tecnology.entity.Persona;
import com.development.ecommerce_tecnology.entity.Rol;
import com.development.ecommerce_tecnology.entity.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UsuarioPersonaDto {

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

    // Datos relacionados con datos personales del usuario

    private Long idpersona;
    private String nombresPersona;
    private String apellidosPersona;
    private String tipoDocumento;
    private String numeroDocumento;
    private LocalDate fechaNacimiento;
    private String sexo;
    private String telefono;
    private String correoSecundario;
    private String direccion;
    private String ciudad;
    private String pais;

    // Imagnes asociada al Usuario
    private ImagenDto imagenUsuario;

    public UsuarioPersonaDto() {

    }


    // Constructor que inicializa todos los datos desde una entidad usuario y sus lista de imágenes
    public UsuarioPersonaDto(Usuario usuario, ImagenDto imagenUsuario) {

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
        }

        // Asigna imagenes asociada
        this.imagenUsuario = imagenUsuario;


        // Extrae datos de persona si existe
        Persona persona = usuario.getPersona() ;
        if (persona!= null) {
            this.idpersona = persona.getIdpersona();
            this.nombresPersona = persona.getNombresPersona();
            this.apellidosPersona = persona.getApellidosPersona();
            this.tipoDocumento = persona.getTipoDocumento();
            this.numeroDocumento = persona.getNumeroDocumento();
            this.fechaNacimiento = persona.getFechaNacimiento();
            this.sexo = persona.getSexo();
            this.telefono = persona.getTelefono();
            this.correoSecundario = persona.getCorreoSecundario();
            this.direccion = persona.getDireccion();
            this.ciudad = persona.getCiudad();
            this.pais = persona.getPais();
        }
    }
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

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public Long getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Long idpersona) {
        this.idpersona = idpersona;
    }

    public String getNombresPersona() {
        return nombresPersona;
    }

    public void setNombresPersona(String nombresPersona) {
        this.nombresPersona = nombresPersona;
    }

    public String getApellidosPersona() {
        return apellidosPersona;
    }

    public void setApellidosPersona(String apellidosPersona) {
        this.apellidosPersona = apellidosPersona;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoSecundario() {
        return correoSecundario;
    }

    public void setCorreoSecundario(String correoSecundario) {
        this.correoSecundario = correoSecundario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
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

    public ImagenDto getImagenUsuario() {
        return imagenUsuario;
    }

    public void setImagenUsuario(ImagenDto imagenUsuario) {
        this.imagenUsuario = imagenUsuario;
    }
}
