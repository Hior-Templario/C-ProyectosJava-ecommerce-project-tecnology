package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dto.UsuarioCrearDto;
import com.development.ecommerce_tecnology.dto.UsuarioPersonaCrearDto;
import com.development.ecommerce_tecnology.entity.EstadoUsuario;
import com.development.ecommerce_tecnology.entity.Persona;
import com.development.ecommerce_tecnology.entity.Rol;
import com.development.ecommerce_tecnology.entity.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioFactory {

    private final PasswordEncoder passwordEncoder;

    public UsuarioFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario crearUsuario(
            UsuarioCrearDto usuarioCrearDto,
            Rol rol ,
            EstadoUsuario estadoUsuario) {

        Usuario usuario = new Usuario();

        usuario.setNombreUsuario(usuarioCrearDto.getNombreUsuario());
        usuario.setCorreo(usuarioCrearDto.getCorreo());
        usuario.setContrasenia(passwordEncoder.encode(usuarioCrearDto.getContrasenia()));
        usuario.setFechaRegistro(usuarioCrearDto.getFechaRegistro());
        usuario.setRol(rol);
        usuario.setEstadoUsuario(estadoUsuario);

        return usuario;
    }

    public Usuario crearUsuarioConPersona(
            UsuarioPersonaCrearDto usuarioPersonCrearDto ,
            Rol rol ,
            EstadoUsuario estadoUsuario){

        Usuario usuario = new Usuario();

        usuario.setNombreUsuario(usuarioPersonCrearDto.getNombreUsuario());
        usuario.setCorreo(usuarioPersonCrearDto.getCorreo());
        usuario.setContrasenia(passwordEncoder.encode(usuarioPersonCrearDto.getContrasenia()));
        usuario.setFechaRegistro(usuarioPersonCrearDto.getFechaRegistro());
        usuario.setRol(rol);
        usuario.setEstadoUsuario(estadoUsuario);

        Persona persona = new Persona();

        persona.setNombresPersona(usuarioPersonCrearDto.getPersonaDto().getNombresPersona());
        persona.setApellidosPersona(usuarioPersonCrearDto.getPersonaDto().getApellidosPersona());
        persona.setTipoDocumento(usuarioPersonCrearDto.getPersonaDto().getTipoDocumento());
        persona.setNumeroDocumento(usuarioPersonCrearDto.getPersonaDto().getNumeroDocumento());
        persona.setFechaNacimiento(usuarioPersonCrearDto.getPersonaDto().getFechaNacimiento());
        persona.setSexo(usuarioPersonCrearDto.getPersonaDto().getSexo());
        persona.setTelefono(usuarioPersonCrearDto.getPersonaDto().getTelefono());
        persona.setCorreoSecundario(usuarioPersonCrearDto.getPersonaDto().getCorreoSecundario());
        persona.setDireccion(usuarioPersonCrearDto.getPersonaDto().getDireccion());
        persona.setCiudad(usuarioPersonCrearDto.getPersonaDto().getCiudad());
        persona.setPais(usuarioPersonCrearDto.getPersonaDto().getPais());

        usuario.setPersona(persona);
        return usuario;
    }
}
