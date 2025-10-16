package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dto.EstadoUsuarioDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EstadoUsuarioService {

    List<EstadoUsuarioDto> obtenerTodosEstadosUsuario();

}
