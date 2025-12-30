package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dao.EstadoUsuarioRepository;
import com.development.ecommerce_tecnology.dto.EstadoUsuarioDto;
import com.development.ecommerce_tecnology.entity.EstadoUsuario;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadoUsuarioServiceImpl implements EstadoUsuarioService{

    private final EstadoUsuarioRepository estadoUsuarioRepository;

    public EstadoUsuarioServiceImpl(EstadoUsuarioRepository estadoUsuarioRepository) {
        this.estadoUsuarioRepository = estadoUsuarioRepository;
    }

    @Override
    public EstadoUsuario obtemerUsarioEstadoEntidad(Long idEstado) {
        return estadoUsuarioRepository.findById(idEstado)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "EstadoUsuario no encontrado"
                ));
    }

    public List<EstadoUsuarioDto> obtenerTodosEstadosUsuario(){

        List<EstadoUsuario> estadosUsuario = estadoUsuarioRepository.findAll();

        return estadosUsuario.stream()
                .map(EstadoUsuarioDto::new)
                .collect(Collectors.toList());
    }
}
