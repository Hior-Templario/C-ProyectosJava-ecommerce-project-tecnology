package com.development.ecommerce_tecnology.dto;

import com.development.ecommerce_tecnology.entity.Categoria;
import com.development.ecommerce_tecnology.entity.Marca;
import com.development.ecommerce_tecnology.entity.Producto;



import java.time.LocalDateTime;
import java.util.List;

// Dto (Data Transfer Object) que representa un producto con sus imagenes asociadas,
// asi como informacion de su categoria y marca

public class ProductoDto {

    // Atributos del producto
    private Long idProducto;
    private String codigoProducto;
    private String nombreProducto;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;

    // Datos relacionados con la categoria del producto
    private Long idCategoria;
    private String nombreCategoria;

    // Datos relacionados con la marca del producto
    private Long idMarca;
    private String nombreMarca;

    // Lista de imagnes asociadas al producto, su categoria y marca
    private List<ImagenDto> imagenesProducto;
    private List<ImagenDto> imagenesCategoria;
    private List<ImagenDto> imagenesMarca;

    public ProductoDto() {
    }

    // Constructor que inicializa todos los datos desde una entidad producto y suslista de imágenes
    public ProductoDto (Producto producto , List<ImagenDto> imagenesProducto, List<ImagenDto> imagenesCategoria , List<ImagenDto> imagenesMarca){

        this.idProducto = producto.getIdProducto();
        this.codigoProducto = producto.getCodigoProducto();
        this.nombreProducto = producto.getNombreProducto();
        this.descripcion = producto.getDescripcion();
        this.precio = producto.getPrecio();
        this.stock = producto.getStock();
        this.fechaRegistro = producto.getFechaRegistro();
        this.fechaActualizacion = producto.getFechaActualizacion();

        // Extrae datos de la categoria si existe
        Categoria categoria = producto.getCategoria();
        if (categoria !=null) {
            this.idCategoria = categoria.getIdCategoria();
            this.nombreCategoria = categoria.getNombreCategoria();
        }

        // Extrae datos de la marca si existe
        Marca marca = producto.getMarca();
        if (marca != null) {
            this.idMarca = marca.getIdMarca();
            this.nombreMarca = marca.getNombreMarca();
        }

        // Asigna lista de imagenes asociadas
        this.imagenesProducto = imagenesProducto;
        this.imagenesCategoria = imagenesCategoria;
        this.imagenesMarca = imagenesMarca;


    }

    // Métodos getter y setter para acceder y modificar atributos
    public Long getIdProducto() {
        return idProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }


    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public Long getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Long idMarca) {
        this.idMarca = idMarca;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    public List<ImagenDto> getImagenesMarca() {
        return imagenesMarca;
    }

    public void setImagenesMarca(List<ImagenDto> imagenesMarca) {
        this.imagenesMarca = imagenesMarca;
    }

    public List<ImagenDto> getImagenesCategoria() {
        return imagenesCategoria;
    }

    public void setImagenesCategoria(List<ImagenDto> imagenesCategoria) {
        this.imagenesCategoria = imagenesCategoria;
    }

    public List<ImagenDto> getImagenesProducto() {
        return imagenesProducto;
    }

    public void setImagenesProducto(List<ImagenDto> imagenesProducto) {
        this.imagenesProducto = imagenesProducto;
    }

}
