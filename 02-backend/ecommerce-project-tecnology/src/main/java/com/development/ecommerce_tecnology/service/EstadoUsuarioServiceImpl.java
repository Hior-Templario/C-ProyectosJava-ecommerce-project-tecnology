package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dao.EstadoUsuarioRepository;
import com.development.ecommerce_tecnology.dto.EstadoUsuarioDto;
import com.development.ecommerce_tecnology.entity.EstadoUsuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadoUsuarioServiceImpl implements EstadoUsuarioService{

    private final EstadoUsuarioRepository estadoUsuarioRepository;

    public EstadoUsuarioServiceImpl(EstadoUsuarioRepository estadoUsuarioRepository) {
        this.estadoUsuarioRepository = estadoUsuarioRepository;
    }

    public List<EstadoUsuarioDto> obtenerTodosEstadosUsuario(){

        List<EstadoUsuario> estadosUsuario = estadoUsuarioRepository.findAll();

        return estadosUsuario.stream()
                .map(EstadoUsuarioDto::new)
                .collect(Collectors.toList());
    }
}
