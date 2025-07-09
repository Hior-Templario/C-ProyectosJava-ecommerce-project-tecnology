package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.entity.Categoria;

import java.util.List;

public interface CategoriaService {

    Categoria crear(Categoria categoria);

    Categoria obtenerCategoriaConImagenes(Long idCategoria);

    List<Categoria> obtenerTodasCategoriasConImagenes();

    void eliminar (Long idCategoria);

}

