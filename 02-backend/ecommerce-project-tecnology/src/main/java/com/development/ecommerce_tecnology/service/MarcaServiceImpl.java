package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dao.ImagenRepository;
import com.development.ecommerce_tecnology.dao.MarcaRepository;
import com.development.ecommerce_tecnology.dto.ImagenCrearDto;
import com.development.ecommerce_tecnology.dto.ImagenDto;
import com.development.ecommerce_tecnology.dto.MarcaCrearDto;
import com.development.ecommerce_tecnology.dto.MarcaDto;

import com.development.ecommerce_tecnology.entity.Imagen;
import com.development.ecommerce_tecnology.entity.Marca;
import com.development.ecommerce_tecnology.enums.TipoEntidad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MarcaServiceImpl implements MarcaService{

    // Logger para registrar eventos importantes o mensajes de depuración
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    private final MarcaRepository marcaRepository;
    private final ImagenRepository imagenRepository;
    private final AmazonS3Service amazonS3Service;

    public MarcaServiceImpl(MarcaRepository marcaRepository, ImagenRepository imagenRepository, AmazonS3Service amazonS3Service) {
        this.marcaRepository = marcaRepository;
        this.imagenRepository = imagenRepository;
        this.amazonS3Service = amazonS3Service;
    }


    @Override
    @Transactional(readOnly = true)
    public MarcaDto obtenerMarcaConImagenes(Long idMarca) {

        Marca marca = marcaRepository.findById(idMarca)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca no encontrada"));

        List<Imagen> imagenes = imagenRepository.findByTipoAndIdEntidad(TipoEntidad.MARCA, idMarca);

        List<ImagenDto> imagenesMarcaDto = imagenes.stream()
                .map(ImagenDto::new)
                .collect(Collectors.toList());
        return new MarcaDto(marca , imagenesMarcaDto);

    }

    @Override
    @Transactional(readOnly = true)
    public List<MarcaDto> obtenerTodasMarcasConImagenes() {

        List<Marca> marcas = marcaRepository.findAll();

        List<Long> idsMarca = marcas.stream()
                .map(Marca::getIdMarca)
                .collect(Collectors.toList());

        Map<Long, List<Imagen>> imagenesPorCategoria = imagenRepository.findByTipoAndIdEntidadIn(TipoEntidad.MARCA, idsMarca)
                .stream().collect(Collectors.groupingBy(Imagen::getIdEntidad));

        return marcas.stream()
                .map(marca -> {
                    List<Imagen> imagenes = imagenesPorCategoria.getOrDefault(
                            marca.getIdMarca() , Collections.emptyList()
                    );

                    List<ImagenDto> imagenMarcaDtos = imagenes.stream()
                            .map(ImagenDto :: new)
                            .collect(Collectors.toList());

                    return new MarcaDto(marca,imagenMarcaDtos);

                }).collect(Collectors.toList());
    }


    @Override
    public MarcaDto crearMarcaConImagen(MarcaCrearDto marcaCrearDto)  throws IOException {

        // Construir entidad marca desde el Dto y guardar en BD

        Marca marca = construirMarcaDesdeDto(marcaCrearDto);
        marcaRepository.save(marca);

        // Obtener la imagen enviada desde Dto
        ImagenCrearDto img = marcaCrearDto.getImagenMarca();

        // Validar si hay archivo de imagen válida
        if (img !=null && img.getArchivo() !=null && !img.getArchivo().isEmpty()){
            logger.info("Imagen recibida");

            // generar nombre unico para el archivo
            String nombreArchivo = UUID.randomUUID() + "_" + img.getArchivo().getOriginalFilename();
            if (img.getTipo() != TipoEntidad.MARCA) {
                throw new IllegalArgumentException("Solo se permite tipo Marca");
            }
            Long idEntidad = marca.getIdMarca();
            // Subir archivo a Amazon S3
            amazonS3Service.subirArchivo(img.getArchivo(), nombreArchivo,img.getTipo(),idEntidad);
        }

        else {

            System.out.println("Imagen No Recibida");

        }

        return obtenerMarcaConImagenes(marca.getIdMarca());
    }

    // Metodo auxiliar para construir un objeto marca desdes DTO

    private Marca construirMarcaDesdeDto(MarcaCrearDto marcaCrearDto){

        Marca marca = new Marca();
         marca.setNombreMarca(marcaCrearDto.getNombreMarca());
         marca.setDescripcion(marcaCrearDto.getDescripcion());

         return marca;
    }

    @Override
    public void eliminarMarca(Long idMarca) {

        marcaRepository.deleteById(idMarca);

    }
}
