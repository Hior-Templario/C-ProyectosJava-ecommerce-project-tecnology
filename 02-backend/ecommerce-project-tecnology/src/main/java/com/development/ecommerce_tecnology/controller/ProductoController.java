package com.development.ecommerce_tecnology.controller;

import com.development.ecommerce_tecnology.entity.Producto;
import com.development.ecommerce_tecnology.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;


    public ProductoController(ProductoService productoService){
        this.productoService =productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>>obtenerTodos(){
        return ResponseEntity.ok(productoService.obtenerTodos());
    }


    @GetMapping("/{idProducto}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long idProducto){
        return  ResponseEntity.ok(productoService.obtenerProductoPorId(idProducto));

    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<Producto>> obtenerPorCategoria(@PathVariable Long idCategoria){
        return ResponseEntity.ok(productoService.obtenerPorCategoria(idCategoria));
    }

    @GetMapping("/marca/{idMarca}")
    public ResponseEntity<List<Producto>> obtenerPorMarca(@PathVariable Long idMarca){
        return ResponseEntity.ok(productoService.obtenerPorMarca(idMarca));
    }

    @PostMapping("/guardar")
    public  ResponseEntity<Producto> crearProducto(@RequestBody Producto producto){
        return new ResponseEntity<>(productoService.guardar(producto), HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{idProducto}")
    public ResponseEntity<Producto> eliminarProducto(@PathVariable Long idProducto){
        productoService.eliminar(idProducto);
        return ResponseEntity.noContent().build();
    }

}



