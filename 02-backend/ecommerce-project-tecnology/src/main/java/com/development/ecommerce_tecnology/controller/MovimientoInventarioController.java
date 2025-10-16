package com.development.ecommerce_tecnology.controller;


import com.development.ecommerce_tecnology.dto.MovimientoInventarioDto;
import com.development.ecommerce_tecnology.dto.MovimientoInventarioRegistrarDto;
import com.development.ecommerce_tecnology.service.MovimientoInventarioRegistrarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimientoInventario")
public class MovimientoInventarioController {

    private  final MovimientoInventarioRegistrarService movimientoInventarioRegistrarService;


    public MovimientoInventarioController(MovimientoInventarioRegistrarService movimientoInventarioRegistrarService) {
        this.movimientoInventarioRegistrarService = movimientoInventarioRegistrarService;
    }


    @PostMapping("/registrarMovimientoInventario")
    public ResponseEntity<MovimientoInventarioDto> crearMovmientoInventario(@RequestBody MovimientoInventarioRegistrarDto movimientoInventarioRegistrarDto){

        MovimientoInventarioDto movimientoCreado = movimientoInventarioRegistrarService.movimientoInventarioRegistrar(movimientoInventarioRegistrarDto);

    return ResponseEntity.ok(movimientoCreado);
    }

    @GetMapping("/{idMovimiento}")
    public ResponseEntity<MovimientoInventarioDto> obtenerMovimientoInventario(@PathVariable Long idMovimiento){

        MovimientoInventarioDto  movimientoInventarioDto = movimientoInventarioRegistrarService.obtenerMovimientoInventarioDto(idMovimiento);
        return ResponseEntity.ok(movimientoInventarioDto);
    }

}
