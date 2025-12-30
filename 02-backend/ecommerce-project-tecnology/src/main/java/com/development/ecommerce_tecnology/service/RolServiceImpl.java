package com.development.ecommerce_tecnology.service;


import com.development.ecommerce_tecnology.dao.RolRepository;
import com.development.ecommerce_tecnology.dto.RolDto;
import com.development.ecommerce_tecnology.entity.Rol;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements  RolService{

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public Rol obtenerRolEntidad(Long idRol) {

        return rolRepository.findById(idRol)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Rol no encontrado"));
    }

    public List<RolDto> obtenerTodosRoles(){

        List<Rol> roles = rolRepository.findAll();

        return  roles.stream()
                .map(RolDto::new)
                .collect(Collectors.toList());
    }

}
