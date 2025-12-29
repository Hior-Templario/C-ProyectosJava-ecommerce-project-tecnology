package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dto.UsuarioCrearDto;
import com.development.ecommerce_tecnology.dto.UsuarioDto;
import com.development.ecommerce_tecnology.dto.UsuarioPersonaCrearDto;
import com.development.ecommerce_tecnology.dto.UsuarioPersonaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface UsuarioService {


    UsuarioDto obtenerUsuarioConImagen(Long idUsuario);
    Page<UsuarioDto> obtenerTodosProductosConImagenesPaginados(Pageable pageable);
    UsuarioPersonaDto obtenerUsuarioPersonaConImagen(Long idUsuario);
    Page<UsuarioPersonaDto> obtenerTodosUsuariosPersonasConImagenPaginados(Pageable pageable);
    UsuarioDto crearUsuarioConImagen(UsuarioCrearDto usuarioDto) throws IOException;
    UsuarioPersonaDto crearUsuarioPersonaConImagen(UsuarioPersonaCrearDto usuarioDto) throws IOException;


}
