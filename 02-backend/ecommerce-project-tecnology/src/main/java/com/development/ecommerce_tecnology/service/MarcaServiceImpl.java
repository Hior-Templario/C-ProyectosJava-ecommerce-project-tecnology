package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dao.ImagenRepository;
import com.development.ecommerce_tecnology.dao.MarcaRepository;
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


import java.util.stream.Collectors;

@Service
public class MarcaServiceImpl implements MarcaService{

    // Logger para registrar eventos importantes o mensajes de depuración
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    private final MarcaRepository marcaRepository;
    private final ImagenService imagenService;
    private final AmazonS3Service amazonS3Service;

    public MarcaServiceImpl(MarcaRepository marcaRepository, ImagenRepository imagenRepository, ImagenService imagenService, AmazonS3Service amazonS3Service) {
        this.marcaRepository = marcaRepository;
        this.imagenService = imagenService;
        this.amazonS3Service = amazonS3Service;

    }


    @Override
    public Marca obtenerMarcaPorId(Long idMarca) {

        return marcaRepository.findById(idMarca).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Narca bno encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public MarcaDto obtenerMarcaConImagenes(Long idMarca) {

        // Busca marca en base de datos
        Marca marca = marcaRepository.findById(idMarca)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca no encontrada"));

        // Obtiene las imagenes asociadas a la marca
        List<Imagen> imagenesMarca = imagenService.obtenerImagenesDeEntidad(TipoEntidad.MARCA, idMarca);

       // Validar que la marca tenga imàgenes
        if (imagenesMarca == null  || imagenesMarca.isEmpty()){

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontaron imànebes");
        }

        // Convetir las entidades Imagen a DTO
        List<ImagenDto> imagenMarcaDtos = imagenesMarca.stream()
                .map(ImagenDto::new)
                .collect(Collectors.toList());

        // Retomar DTO completo de la categoria
        return new MarcaDto(marca, imagenMarcaDtos);

    }

    @Override
    @Transactional(readOnly = true)
    public List<MarcaDto> obtenerTodasMarcasConImagenes() {

        // Obtener todas las marcas registradas
        List<Marca> marcas = marcaRepository.findAll();

        // Extraer los IDS de las marcas
        List<Long> idsMarca = marcas.stream()
                .map(Marca::getIdMarca)
                .collect(Collectors.toList());

        // Obtener todas las imagenes agrupadas por ID de marca
        Map<Long, List<Imagen>> imagenesPorCategoria = imagenService.obtenerImagenesDeEntidades(TipoEntidad.MARCA, idsMarca);

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


    // Metodo para  crear una nueva  marca con su respectiva imagen

    @Override
    @Transactional
    public MarcaDto crearMarcaConImagen(MarcaCrearDto marcaCrearDto)  throws IOException {

        // Construir entidad marca desde el Dto y guardar en BD

        Marca marca = construirMarcaDesdeDto(marcaCrearDto);
        marcaRepository.save(marca);

        // Subir imagenes asociadas al producto
        imagenService.subirImagenPorEntidad(
                marcaCrearDto.getImagenMarca(),
                TipoEntidad.MARCA,
                marca.getIdMarca());

        // Retonar DTO con informacion completa del usurio, incluyendo imagen
        return obtenerMarcaConImagenes(marca.getIdMarca());
    }

    // Metodo auxiliar para construir un objeto marca desdes DTO

    // Construye un objeto Marca a partir del DRO de creaciòn
    private Marca construirMarcaDesdeDto(MarcaCrearDto marcaCrearDto){

        Marca marca = new Marca();
         marca.setNombreMarca(marcaCrearDto.getNombreMarca());
         marca.setDescripcion(marcaCrearDto.getDescripcion());

         return marca;
    }

    @Override
    public void eliminarMarca(Long idMarca) {

        // Eliminar la marca
        marcaRepository.deleteById(idMarca);

    }
}
