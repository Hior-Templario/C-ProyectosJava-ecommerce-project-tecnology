package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dao.CategoriaRepository;
import com.development.ecommerce_tecnology.dao.ImagenRepository;
import com.development.ecommerce_tecnology.entity.Categoria;
import com.development.ecommerce_tecnology.entity.Imagen;
import com.development.ecommerce_tecnology.enums.TipoEntidad;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {


    private final CategoriaRepository categoriaRepository;
    private final ImagenRepository imagenRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, ImagenRepository imagenRepository) {
        this.categoriaRepository = categoriaRepository;
        this.imagenRepository = imagenRepository;
    }

    @Override
    public Categoria crear(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria obtenerCategoriaConImagenes(Long idCategoria){

        Categoria categoria  = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
        List<Imagen> imagenes = imagenRepository.findByTipoAndIdEntidad(TipoEntidad.CATEGORIA , idCategoria
        );
        categoria.setImagenes(imagenes);

        return categoria;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Categoria> obtenerTodasCategoriasConImagenes() {

        List<Categoria> categorias = categoriaRepository.findAll();

        List<Long> idsCategoria = categorias.stream()
                .map(Categoria::getIdCategoria)
                .collect(Collectors.toList());

        List<Imagen> imagenes = imagenRepository.findByTipoAndIdEntidadIn(TipoEntidad.CATEGORIA, idsCategoria);

        Map<Long, List<Imagen>> imagenesPorCategoria = imagenes.stream()
                .collect(Collectors.groupingBy(Imagen::getIdEntidad));

        categorias.forEach(categoria ->
                categoria.setImagenes(
                        imagenesPorCategoria.getOrDefault(
                                categoria.getIdCategoria(),
                                Collections.emptyList())));

        return categorias;
    }


    @Override
    public void eliminar(Long idCategoria) {
    if (!categoriaRepository.existsById(idCategoria)){
       throw new EntityNotFoundException("Categoria no encontrada para eliminar");
    }
    categoriaRepository.deleteById(idCategoria);
    }

}
