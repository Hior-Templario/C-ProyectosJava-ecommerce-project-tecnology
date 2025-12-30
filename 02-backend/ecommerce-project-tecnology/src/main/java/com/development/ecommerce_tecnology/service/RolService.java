package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dto.RolDto;
import com.development.ecommerce_tecnology.entity.Rol;

import java.util.List;

public interface RolService {

    // para usi interno (negocio)
    Rol obtenerRolEntidad(Long idRol);

    List<RolDto> obtenerTodosRoles();
}
