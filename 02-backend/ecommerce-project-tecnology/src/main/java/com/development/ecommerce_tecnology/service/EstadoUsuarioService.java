package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dto.EstadoUsuarioDto;
import com.development.ecommerce_tecnology.entity.EstadoUsuario;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EstadoUsuarioService {

    EstadoUsuario obtemerUsarioEstadoEntidad(Long idEstado);

    List<EstadoUsuarioDto> obtenerTodosEstadosUsuario();

}
