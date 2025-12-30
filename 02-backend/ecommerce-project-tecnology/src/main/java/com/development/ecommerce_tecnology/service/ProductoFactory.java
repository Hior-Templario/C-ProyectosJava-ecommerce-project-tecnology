package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dto.ProductoCrearDto;
import com.development.ecommerce_tecnology.entity.Categoria;
import com.development.ecommerce_tecnology.entity.Marca;
import com.development.ecommerce_tecnology.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoFactory {

    //Construir  Producto desde Dto
    public Producto crearProducto(ProductoCrearDto productoDto,
                                   Categoria categoria,
                                   Marca marca,
                                   String codigoGenerado) {

        Producto producto = new Producto();
        producto.setCodigoProducto(codigoGenerado);
        producto.setNombreProducto(productoDto.getNombreProducto());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setPrecio(productoDto.getPrecio());
        producto.setStock(0);
        producto.setFechaRegistro(productoDto.getFechaRegistro());
        producto.setFechaActualizacion(productoDto.getFechaActualizacion());
        producto.setCategoria(categoria);
        producto.setMarca(marca);

        return producto;
    }
}
