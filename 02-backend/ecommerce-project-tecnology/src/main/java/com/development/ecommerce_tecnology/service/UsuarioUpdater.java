package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dto.UsuarioActualizarDto;
import com.development.ecommerce_tecnology.entity.EstadoUsuario;
import com.development.ecommerce_tecnology.entity.Rol;
import com.development.ecommerce_tecnology.entity.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioUpdater {

    // Inyección de dependencias
    private final PasswordEncoder passwordEncoder;


    public UsuarioUpdater(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // Aplica los cambio  al usuario existente
    public void actualizarUsuario(
            Usuario usuario,
            UsuarioActualizarDto usuarioActualizarDto,
            Rol rol,
            EstadoUsuario estadoUsuario){

        // Actualizacion de datos basico
        if(usuarioActualizarDto.getNombreUsuario() != null) {
            usuario.setNombreUsuario(usuarioActualizarDto.getNombreUsuario());
        }

        if(usuarioActualizarDto.getCorreo() != null) {
            usuario.setCorreo(usuarioActualizarDto.getCorreo());
        }

        if (rol != null) {
            usuario.setRol(rol);
        }

        if(estadoUsuario != null) {
            usuario.setEstadoUsuario(estadoUsuario);
        }

        if ((usuarioActualizarDto.getContrasenia() !=null && !usuarioActualizarDto.getContrasenia().isBlank())){
            usuario.setContrasenia(passwordEncoder.encode(usuarioActualizarDto.getContrasenia()));
        }
    }
}
