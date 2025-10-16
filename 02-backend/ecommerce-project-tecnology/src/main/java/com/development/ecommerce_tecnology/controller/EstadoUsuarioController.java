package com.development.ecommerce_tecnology.controller;

import com.development.ecommerce_tecnology.dto.EstadoUsuarioDto;
import com.development.ecommerce_tecnology.service.EstadoUsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estadosUsuario")
public class EstadoUsuarioController {

    EstadoUsuarioService estadoUsuarioService;

    public EstadoUsuarioController(EstadoUsuarioService estadoUsuarioService) {
        this.estadoUsuarioService = estadoUsuarioService;
    }

    @GetMapping
    public ResponseEntity <List<EstadoUsuarioDto>> obtenerTodosEstadosUsuario(){
        return ResponseEntity.ok(estadoUsuarioService.obtenerTodosEstadosUsuario());
    }
}
