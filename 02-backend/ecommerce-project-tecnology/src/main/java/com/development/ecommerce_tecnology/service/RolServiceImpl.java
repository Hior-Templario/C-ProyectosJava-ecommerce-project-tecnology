package com.development.ecommerce_tecnology.service;


import com.development.ecommerce_tecnology.dao.RolRepository;
import com.development.ecommerce_tecnology.dto.RolDto;
import com.development.ecommerce_tecnology.entity.Rol;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements  RolService{

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<RolDto> obtenerTodosRoles(){

        List<Rol> roles = rolRepository.findAll();

        return  roles.stream()
                .map(RolDto::new)
                .collect(Collectors.toList());
    }

}
