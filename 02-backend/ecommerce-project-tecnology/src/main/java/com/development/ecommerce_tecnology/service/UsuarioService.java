package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dto.UsuarioCrearDto;
import com.development.ecommerce_tecnology.dto.UsuarioDto;
import com.development.ecommerce_tecnology.dto.UsuarioPersonaCrearDto;
import com.development.ecommerce_tecnology.dto.UsuarioPersonaDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface UsuarioService {


    UsuarioDto obtenerUsuarioConImagen(Long idUsuario);
    List<UsuarioDto> obtenerTodosUsuariosConImagen();
    UsuarioPersonaDto obtenerUsuarioPersonaConImagen(Long idUsuario);
    List<UsuarioPersonaDto> obtenerTodosUsuariosPersonasConImagen();
    UsuarioDto crearUsuarioConImagen(UsuarioCrearDto usuarioDto) throws IOException;
    UsuarioPersonaDto crearUsuarioPersonaConImagen(UsuarioPersonaCrearDto usuarioDto) throws IOException;


}
