package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    Producto obtenerProductoPorId(Long idProducto);

    List<Producto> obtenerTodos();

    Producto crear(Producto producto);

    void eliminar(Long idProducto);

    List<Producto> obtenerPorCategoria( Long idCategoria);

    List<Producto> obtenerPorMarca(Long idMarca);

}
