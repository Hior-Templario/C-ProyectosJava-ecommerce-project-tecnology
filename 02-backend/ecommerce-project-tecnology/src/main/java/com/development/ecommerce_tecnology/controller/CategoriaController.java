package com.development.ecommerce_tecnology.controller;

import java.util.List;

import com.development.ecommerce_tecnology.entity.Categoria;
import com.development.ecommerce_tecnology.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {


    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerCategoriasConImagenes(){
        return ResponseEntity.ok(categoriaService.obtenerTodasCategoriasConImagenes());

    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<Categoria> obtenerCategoriaConImagenes(@PathVariable Long idCategoria) {
        Categoria categoria = categoriaService.obtenerCategoriaConImagenes(idCategoria);
        return ResponseEntity.ok(categoria);

    }

    @PostMapping("/crear")
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria){

        Categoria nuevaCategoria = categoriaService.crear(categoria);
                return ResponseEntity.ok(nuevaCategoria);
    }

}
