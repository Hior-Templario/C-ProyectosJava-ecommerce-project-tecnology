package com.development.ecommerce_tecnology.mapper;

import com.development.ecommerce_tecnology.dto.ImagenDto;
import com.development.ecommerce_tecnology.dto.ProductoDto;
import com.development.ecommerce_tecnology.entity.Imagen;
import com.development.ecommerce_tecnology.entity.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProductoMapper {

    // Logger para registrar eventos importantes o advertencias
    private static final Logger logger = LoggerFactory.getLogger(ProductoMapper.class);

    /**
     * Metódo principal del mapper. convierte un objeto a productoDto
     * incluyendo imagenes asociadas al producto, su categoria y su marca
     *
     * @param producto La entidad producto original
     * @param imagenesProductoDto  Mapa de ID de producto a lista de imágenes del producto
     * @param imagenesCategoriaDto  Mapa de ID de categoría a lista de imágenes de la categoria
     * @param imagenesMarcaDto  Mapa de ID de marca a lista de imágenes de la marca
     * @param productoDto DTO listo para enviar al frontend
     */
    public ProductoDto mappearProductoDto(Producto producto,
                                           Map<Long, List<Imagen>> imagenesProductoDto,
                                           Map<Long, List<Imagen>> imagenesCategoriaDto,
                                           Map<Long, List<Imagen>> imagenesMarcaDto){

        // Crear nuevo DTO vacio
        ProductoDto productoDto = new ProductoDto();

        // Copiar atibutos basicos del producto// Copiar atibutos basicos del producto
        productoDto.setIdProducto(producto.getIdProducto());
        productoDto.setCodigoProducto(producto.getCodigoProducto());
        productoDto.setNombreProducto(producto.getNombreProducto());
        productoDto.setDescripcion(producto.getDescripcion());
        productoDto.setPrecio(producto.getPrecio());
        productoDto.setFechaRegistro(producto.getFechaRegistro());
        productoDto.setFechaActualizacion(producto.getFechaActualizacion());
        productoDto.setStock(producto.getStock());

        // Procesar informaciòn de las categoria si èsta presente
        if (producto.getCategoria() != null) {
            Long idCategoria = producto.getCategoria().getIdCategoria();

            productoDto.setIdCategoria(idCategoria);
            logger.info("ID Categoria desdes PoductoMapper DTO: {}", productoDto.getIdCategoria());
            productoDto.setNombreCategoria(producto.getCategoria().getNombreCategoria());


            // Convertir lista de entidades Imagen a DTOs y asignarlas al productoDto
            productoDto.setImagenesCategoria(
                    imagenesCategoriaDto.
                            getOrDefault(idCategoria, List.of())
                            .stream()
                            .map(ImagenDto::new)
                            .toList());
        }

        else {
            // Log de advertencia si el producto no tiene categoria
            logger.warn("ID Categoria desde ProductoMapper no llegó");

        }

        // Procesar informaciòn de las marca si èsta presente
        if (producto.getMarca() != null) {

            Long idMarca = producto.getMarca().getIdMarca();
            productoDto.setIdMarca(idMarca);
            productoDto.setNombreMarca(producto.getMarca().getNombreMarca());

            // Convertir lista de entidades Imagen a DTOs y asignarlas al productoDto
            productoDto.setImagenesMarca(
                    imagenesMarcaDto.
                            getOrDefault(idMarca, List.of())
                            .stream()
                            .map(ImagenDto::new)
                            .toList());

        }

        // Procesar imàgenes propias del producto
        Long idProducto = producto.getIdProducto();
        productoDto.setImagenesProducto(imagenesProductoDto.getOrDefault(idProducto, List.of())
                .stream().map(ImagenDto::new).toList());

        // Devolver el DTO completamente armado
        return productoDto;

    }
}
