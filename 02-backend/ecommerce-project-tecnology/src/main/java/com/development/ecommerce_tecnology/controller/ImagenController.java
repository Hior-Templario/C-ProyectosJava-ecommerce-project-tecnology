package com.development.ecommerce_tecnology.controller;

import com.development.ecommerce_tecnology.dao.ImagenRepository;
import com.development.ecommerce_tecnology.enums.TipoEntidad;
import com.development.ecommerce_tecnology.service.AmazonS3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/imagenes")
public class ImagenController {

    private final AmazonS3Service s3Service;
    private final ImagenRepository imagenRepository;

    public ImagenController(AmazonS3Service s3Service, ImagenRepository imagenRepository) {
        this.s3Service = s3Service;
        this.imagenRepository = imagenRepository;
    }

    @PostMapping("/subir")
    public ResponseEntity<String> subirArchivo(
            @RequestParam("archivo")MultipartFile archivo,
            @RequestParam("tipo")TipoEntidad tipo,
            @RequestParam("idEntidad") Long idEntidad){

        try{

            String nombre = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
            String  url = s3Service.subirArchivo(archivo, nombre, tipo, idEntidad);
            return ResponseEntity.ok(url);
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir imagen");
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void>eliminar(@RequestParam  String nombreArchivo){
        s3Service.eliminarArchivo(nombreArchivo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/eliminar-por-entidad")
    public ResponseEntity<Void>eliminarPorEntidad(@RequestParam TipoEntidad tipo, @RequestParam Long idEntidad ){
        s3Service.eliminarArchivoPorEntidad(tipo, idEntidad);
        return ResponseEntity.noContent().build();
    }

}
