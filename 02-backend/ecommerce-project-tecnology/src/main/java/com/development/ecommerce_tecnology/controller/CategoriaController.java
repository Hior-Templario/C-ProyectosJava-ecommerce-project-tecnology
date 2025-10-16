package com.development.ecommerce_tecnology.controller;

import java.io.IOException;
import java.util.List;

import com.development.ecommerce_tecnology.dto.CategoriaCrearDto;
import com.development.ecommerce_tecnology.dto.CategoriaDto;
import com.development.ecommerce_tecnology.dto.ImagenCrearDto;
import com.development.ecommerce_tecnology.enums.TipoEntidad;
import com.development.ecommerce_tecnology.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {


    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> obtenerCategoriasConImagenes(){
        return ResponseEntity.ok(categoriaService.obtenerTodasCategoriasConImagenes());

    }

    @GetMapping("/detallesProducto/{idCategoria}")
    public ResponseEntity<CategoriaDto> obtenerCategoriaConImagenes(@PathVariable Long idCategoria) {
        CategoriaDto categoriaDto = categoriaService.obtenerCategoriaConImagenes(idCategoria);
        return ResponseEntity.ok(categoriaDto);

    }

    @PostMapping(value = "/crearCategoria" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoriaDto> crearCategoria(

            @RequestParam("nombreCategoria") String nombreCategoria,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("prefijoCategoria") String prefijoCategoria,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            @RequestParam(value = "tipo", required = false) TipoEntidad tipo
    ) throws IOException {

        CategoriaCrearDto categoriaCrearDto = new CategoriaCrearDto();
        categoriaCrearDto.setNombreCategoria(nombreCategoria);
        categoriaCrearDto.setDescripcion(descripcion);
        categoriaCrearDto.setPrefijoCategoria(prefijoCategoria);

        if (archivo != null && !archivo.isEmpty()){
            ImagenCrearDto imagen = new ImagenCrearDto();
            imagen.setArchivo(archivo);
            imagen.setTipo(tipo !=null ? tipo : TipoEntidad.CATEGORIA);
            categoriaCrearDto.setImagenCategoria(imagen);
        }

        CategoriaDto categoriaGuardada = categoriaService.crearCategoriaConImagen(categoriaCrearDto);

        return new ResponseEntity<>(categoriaGuardada, HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{idCategoria}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long idCategoria){
        categoriaService.eliminarCategoria(idCategoria);
        return ResponseEntity.noContent().build();
    }

}
