package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dto.ProductoDto;
import com.development.ecommerce_tecnology.dto.ProductoCrearDto;
import com.development.ecommerce_tecnology.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;


public interface ProductoService {

    ProductoDto obtenerProductoConImagenes(Long idProducto);

    List<ProductoDto> obtenerTodosProductosConImagenes();

    Page<ProductoDto> obtenerTodosProductosConImagenesPaginados(Pageable pageable);

    ProductoDto crearProductoConImagenes(ProductoCrearDto productoDto)throws IOException;

    void eliminarProducto(Long idProducto);

    List<Producto> buscarPorCodigoONombre(String query);

    List<Producto> obtenerProductosPorCategoria( Long idCategoria);

    List<Producto> obtenerProductosPorMarca(Long idMarca);


}
