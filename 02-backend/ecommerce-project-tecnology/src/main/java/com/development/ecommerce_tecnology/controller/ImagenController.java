package com.development.ecommerce_tecnology.controller;

import com.development.ecommerce_tecnology.dao.ImagenRepository;
import com.development.ecommerce_tecnology.entity.Imagen;
import com.development.ecommerce_tecnology.enums.TipoEntidad;
import com.development.ecommerce_tecnology.service.AmazonS3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Map<String , String>> subirArchivo(

            @RequestParam("archivo")MultipartFile archivo,
            @RequestParam("tipo")TipoEntidad tipo,
            @RequestParam("idEntidad") Long idEntidad){

        try{

            String  url = s3Service.subirArchivo(archivo, tipo, idEntidad);

            return ResponseEntity.ok(
                    Map.of("url",url)
            );

        }catch (IOException e){

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error","Error al subir imagen"));
        }
    }

    @DeleteMapping("/eliminar-por-entidad")
    public ResponseEntity<Void>eliminarPorEntidad(@RequestParam TipoEntidad tipo, @RequestParam Long idEntidad ){
        s3Service.eliminarArchivoPorEntidad(tipo, idEntidad);
        return ResponseEntity.noContent().build();
    }

    @PutMapping( value = "/actualizarPorEntidad" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> actualizarImagenesPorEntidad(

            @RequestParam("tipo") TipoEntidad tipoEntidad,
            @RequestParam("idEntidad")  Long idEntidad,
            @RequestParam("imagenes")List<MultipartFile>imagenes
    ){

        try {
            s3Service.eliminarArchivoPorEntidad(tipoEntidad, idEntidad);

            for(MultipartFile archivo : imagenes){

                s3Service.subirArchivo(archivo, tipoEntidad, idEntidad
                );
            }

            return ResponseEntity.noContent().build();

        } catch (IOException e){

            return  ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

        @DeleteMapping("/{id_imagen}")
    public ResponseEntity<Void>eliminarImagen(@PathVariable Long id_imagen){

        Imagen imagen = imagenRepository.findById(id_imagen)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Imagen no encontrada"));

        s3Service.eliminarArchivoPorKey(imagen.getS3key());

        return ResponseEntity.noContent().build();
    }

}
