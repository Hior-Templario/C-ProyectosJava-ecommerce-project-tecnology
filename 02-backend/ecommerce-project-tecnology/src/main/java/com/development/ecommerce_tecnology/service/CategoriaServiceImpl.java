package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dao.CategoriaRepository;
import com.development.ecommerce_tecnology.dao.ImagenRepository;
import com.development.ecommerce_tecnology.dto.CategoriaCrearDto;
import com.development.ecommerce_tecnology.dto.CategoriaDto;
import com.development.ecommerce_tecnology.dto.ImagenCrearDto;
import com.development.ecommerce_tecnology.dto.ImagenDto;
import com.development.ecommerce_tecnology.entity.Categoria;
import com.development.ecommerce_tecnology.entity.Imagen;
import com.development.ecommerce_tecnology.enums.TipoEntidad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.EntityNotFoundException;
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
public class CategoriaServiceImpl implements CategoriaService {

    // Logger para registrar eventos importantes o mensajes de depuración
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);



    private final CategoriaRepository categoriaRepository;
    private final ImagenRepository imagenRepository;
    private final AmazonS3Service amazonS3Service;


    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, ImagenRepository imagenRepository, AmazonS3Service amazonS3Service) {
        this.categoriaRepository = categoriaRepository;
        this.imagenRepository = imagenRepository;

        this.amazonS3Service = amazonS3Service;
    }


    @Override
    @Transactional(readOnly = true)
    public CategoriaDto obtenerCategoriaConImagenes(Long idCategoria){

        Categoria categoria  = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada"));

        List<Imagen> imagenesCategoria = imagenRepository.findByTipoAndIdEntidad(TipoEntidad.CATEGORIA , idCategoria);

        if (imagenesCategoria == null  ||  imagenesCategoria.isEmpty() ){

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron imagenes");
        }

        List<ImagenDto> imagenesCategoriaDto = imagenesCategoria.stream()
                .map(ImagenDto::new)
                .collect(Collectors.toList());

        return new CategoriaDto(categoria ,imagenesCategoriaDto);

    }


    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDto> obtenerTodasCategoriasConImagenes() {

        List<Categoria> categorias = categoriaRepository.findAll();

        List<Long> idsCategoria = categorias.stream()
                .map(Categoria::getIdCategoria)
                .collect(Collectors.toList());

        Map<Long, List<Imagen>> imagenesPorCategoria = imagenRepository.findByTipoAndIdEntidadIn(TipoEntidad.CATEGORIA , idsCategoria)
                .stream().collect(Collectors.groupingBy(Imagen:: getIdEntidad));

        //Converit cada categoria e imagnes a DTO

        return categorias.stream()
                .map(categoria -> {
                    List<Imagen> imagenes = imagenesPorCategoria.getOrDefault(
                            categoria.getIdCategoria(), Collections.emptyList());

                    List<ImagenDto> imagenDtos = imagenes.stream()
                            .map(ImagenDto::new)
                            .collect(Collectors.toList());

                    return  new CategoriaDto(categoria, imagenDtos);
                })
                .collect(Collectors.toList());
    }

    // Metodo para crear una nueva categoria con su respectiva imagen

    @Override
    @Transactional
    public CategoriaDto crearCategoriaConImagen(CategoriaCrearDto categoriaCrearDto) throws IOException {

        //Contruir entidad categoria desde el DTO y guardar en BD

        Categoria categoria = construirCategoriaDesdeDto(categoriaCrearDto);
        categoriaRepository.save(categoria);


        // Obtener la imagen enviada desde DTO
        ImagenCrearDto img = categoriaCrearDto.getImagenCategoria();
        logger.info("Llego imagen?:" + img);


        // Validar si hay archivo de imagen válida
        if (img != null && img.getArchivo() != null && !img.getArchivo().isEmpty()) {
            logger.info("Imagen recibida");

            // Generar nombre unico para el archivo
            String nombreArchivo = UUID.randomUUID() + "_" + img.getArchivo().getOriginalFilename();
            if (img.getTipo() != TipoEntidad.CATEGORIA){
                throw new IllegalArgumentException("Solo se permite imagen tipo categoria");
            }

            Long idEntidad = categoria.getIdCategoria();
            // Subir archivo a Amazon S3
            amazonS3Service.subirArchivo(img.getArchivo(),nombreArchivo,img.getTipo(),idEntidad);
        }
        else {
            System.out.println("Imagen No Recibida");
        }

        // Retornar DTO con información completa del usuario, incluyendo imagen
        return obtenerCategoriaConImagenes(categoria.getIdCategoria());
    }


    // Metodo auxiliar para construir un objeto categoria desdes DTO

    private Categoria construirCategoriaDesdeDto(CategoriaCrearDto categoriaCrearDto){

        Categoria categoria = new Categoria();
        categoria.setNombreCategoria(categoriaCrearDto.getNombreCategoria());
        categoria.setDescripcion(categoriaCrearDto.getDescripcion());
        categoria.setPrefijoCategoria(categoriaCrearDto.getPrefijoCategoria());

        return categoria;

    }






    @Override
    public void eliminarCategoria(Long idCategoria) {
    if (!categoriaRepository.existsById(idCategoria)){
       throw new EntityNotFoundException("Categoria no encontrada para eliminar");
    }
    categoriaRepository.deleteById(idCategoria);
    }

}
