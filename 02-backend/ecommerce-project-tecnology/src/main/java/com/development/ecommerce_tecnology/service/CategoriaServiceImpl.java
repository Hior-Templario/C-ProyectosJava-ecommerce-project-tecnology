package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dao.CategoriaRepository;
import com.development.ecommerce_tecnology.dao.ImagenRepository;
import com.development.ecommerce_tecnology.dto.CategoriaCrearDto;
import com.development.ecommerce_tecnology.dto.CategoriaDto;
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
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    // Repositories y servicios necesarios para operaciones sobre imagenes
    private final CategoriaRepository categoriaRepository;

    // Servicio encargado de la gestion de imagenes (subida, consulta y asociaciòn)
    private final ImagenService imagenService;

    // Servicio para  operaciones con Amazon S3 8almaenamiento de archivos9
    private final AmazonS3Service amazonS3Service;

    // Logger para registrar eventos importantes o mensajes de depuración
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);


    // Constructor para intecciòn de dependencias
    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, ImagenRepository imagenRepository, ImagenService imagenService, AmazonS3Service amazonS3Service) {
        this.categoriaRepository = categoriaRepository;
        this.imagenService = imagenService;
        this.amazonS3Service = amazonS3Service;
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria obtenerCategoriaPorId(Long idCategoria) {

        return  categoriaRepository.findById(idCategoria)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDto obtenerCategoriaConImagenes(Long idCategoria){


        // Busca categoria en base de datos
        Categoria categoria  = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada"));


        // Obtiene las imàgenes asociadas a la cactegoria
        List<Imagen> imagenesCategoria = imagenService.obtenerImagenesDeEntidad(TipoEntidad.CATEGORIA , idCategoria);

        // Validar que la categoria tenga imàgenes
        if (imagenesCategoria == null  ||  imagenesCategoria.isEmpty() ){

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron imagenes");
        }

        // Convertir las entidades Imagen a DTO
        List<ImagenDto> imagenesCategoriaDto = imagenesCategoria.stream()
                .map(ImagenDto::new)
                .collect(Collectors.toList());

        // Retonar DTO completo de la categoria
        return new CategoriaDto(categoria ,imagenesCategoriaDto);

    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDto> obtenerTodasCategoriasConImagenes() {

        // Obtener todas las categorias registradas
        List<Categoria> categorias = categoriaRepository.findAll();

        //Extraer los IDS de las categorias
        List<Long> idsCategoria = categorias.stream()
                .map(Categoria::getIdCategoria)
                .collect(Collectors.toList());

        // Obtener todas las imagenes agrupadas por ID de categoria
        Map<Long, List<Imagen>> imagenesPorCategoria = imagenService.obtenerImagenesDeEntidades(TipoEntidad.CATEGORIA , idsCategoria);

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

        // Subir imagenes asociadas al producto
        imagenService.subirImagenPorEntidad(
                categoriaCrearDto.getImagenCategoria(),
                TipoEntidad.CATEGORIA,
                categoria.getIdCategoria());

        // Retornar DTO con información completa del usuario, incluyendo imagen
        return obtenerCategoriaConImagenes(categoria.getIdCategoria());
    }

    // ======= Metodo auxiliar para construir un objeto categoria desdes DTO ========


    // Construye un objeto Categoria a partir del DTO de creaciòn
    private Categoria construirCategoriaDesdeDto(CategoriaCrearDto categoriaCrearDto){

        Categoria categoria = new Categoria();
        categoria.setNombreCategoria(categoriaCrearDto.getNombreCategoria());
        categoria.setDescripcion(categoriaCrearDto.getDescripcion());
        categoria.setPrefijoCategoria(categoriaCrearDto.getPrefijoCategoria());

        return categoria;
    }

    @Override
    public void eliminarCategoria(Long idCategoria) {

        // Validar que la categoria exista antes de eliminar
    if (!categoriaRepository.existsById(idCategoria)){
       throw new EntityNotFoundException("Categoria no encontrada para eliminar");
    }

    // Eliminar la categoria
    categoriaRepository.deleteById(idCategoria);
    }

    @Override
    @Transactional (readOnly = true)

    public String obtenerPrefijoCategoria(Long idCategoria) {
        String prefijo = categoriaRepository.obtenerPrefijoPorId(idCategoria);

        return prefijo != null? prefijo : "GEN";
    }

}





