package com.development.ecommerce_tecnology.controller;

import com.development.ecommerce_tecnology.entity.Marca;
import com.development.ecommerce_tecnology.service.MarcaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping
    public ResponseEntity<List<Marca>>obtenerTodas(){
        return ResponseEntity.ok(marcaService.obtenerTodas());
    }

}
