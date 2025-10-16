package com.development.ecommerce_tecnology.controller;

import com.development.ecommerce_tecnology.dto.*;
import com.development.ecommerce_tecnology.entity.Marca;
import com.development.ecommerce_tecnology.enums.TipoEntidad;
import com.development.ecommerce_tecnology.service.MarcaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping("/{idMarca}")
    public ResponseEntity<MarcaDto>obtenerMarcaPorId(@PathVariable Long idMarca){
        return ResponseEntity.ok(marcaService.obtenerMarcaConImagenes(idMarca));
    }

    @GetMapping
    public ResponseEntity<List<MarcaDto>>obtenerTodasMarcasConImagenes(){
        return ResponseEntity.ok(marcaService.obtenerTodasMarcasConImagenes());
    }

    @PostMapping(value = "/crearMarca" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MarcaDto> crearMarca(

            @RequestParam("nombreMarca") String nombreMarca,
            @RequestParam("descripcion") String descripcion,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            @RequestParam(value = "tipo", required = false) TipoEntidad tipo
            ) throws IOException {


        MarcaCrearDto marcaCrearDto = new MarcaCrearDto();
        marcaCrearDto.setNombreMarca(nombreMarca);
        marcaCrearDto.setDescripcion(descripcion);

        if (archivo != null && !archivo.isEmpty()){
            ImagenCrearDto imagen = new ImagenCrearDto();
            imagen.setArchivo(archivo);
            imagen.setTipo(tipo !=null ? tipo : TipoEntidad.CATEGORIA);
            marcaCrearDto.setImagenMarca(imagen);
        }

        MarcaDto marcaGuardada = marcaService.crearMarcaConImagen(marcaCrearDto);

        return new ResponseEntity<>(marcaGuardada, HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{idMarca}")
    public ResponseEntity<Marca>eliminar(@PathVariable Long idMarca){
        marcaService.eliminarMarca(idMarca);
        return  ResponseEntity.noContent().build();
    }


}
