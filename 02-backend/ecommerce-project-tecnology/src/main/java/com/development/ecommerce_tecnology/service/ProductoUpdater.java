package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dto.ProductoActualizarDto;
import com.development.ecommerce_tecnology.entity.Categoria;
import com.development.ecommerce_tecnology.entity.Marca;
import com.development.ecommerce_tecnology.entity.Producto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductoUpdater {

    // Actualizar producto existente dese DTO
    public void actualizarProducto(Producto producto,
                                   ProductoActualizarDto productoActualizarDto,
                                   Categoria categoria,
                                   Marca marca) {

        // Actualizacion de datos basico
        if (productoActualizarDto.getNombreProducto() != null){
            producto.setNombreProducto(productoActualizarDto.getNombreProducto());
        }

        if (productoActualizarDto.getDescripcion() != null){
            producto.setDescripcion(productoActualizarDto.getDescripcion());
        }

        if (productoActualizarDto.getPrecio() != null){
            producto.setPrecio(productoActualizarDto.getPrecio());
        }

        if (categoria != null){
            producto.setCategoria(categoria);
        }

        if (marca != null){
            producto.setMarca(marca);
        }


        // Actualizar fecha de modificación
        producto.setFechaActualizacion(LocalDateTime.now());
    }

}
