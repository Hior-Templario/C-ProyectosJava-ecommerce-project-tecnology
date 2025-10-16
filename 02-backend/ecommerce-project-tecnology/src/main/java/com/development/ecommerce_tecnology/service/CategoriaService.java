package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dto.CategoriaCrearDto;
import com.development.ecommerce_tecnology.dto.CategoriaDto;
import com.development.ecommerce_tecnology.entity.Categoria;

import java.io.IOException;
import java.util.List;

public interface CategoriaService {


    CategoriaDto obtenerCategoriaConImagenes(Long idCategoria);

    List<CategoriaDto> obtenerTodasCategoriasConImagenes();

    CategoriaDto crearCategoriaConImagen(CategoriaCrearDto categoriaCrearDto)throws IOException;

    void eliminarCategoria (Long idCategoria);

}

