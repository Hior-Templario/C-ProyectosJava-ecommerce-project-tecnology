package com.development.ecommerce_tecnology.controller;

import com.development.ecommerce_tecnology.dto.RolDto;
import com.development.ecommerce_tecnology.service.RolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public ResponseEntity<List<RolDto>> obtenerTodosRoles(){
        return ResponseEntity.ok(rolService.obtenerTodosRoles());
    }
}
