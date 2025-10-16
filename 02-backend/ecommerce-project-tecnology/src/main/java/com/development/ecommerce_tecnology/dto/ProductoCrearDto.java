package com.development.ecommerce_tecnology.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

// Clase DTO (Data Transfer Object) Que se utiliza para transferir lo datos necesarios  para un nuevo producto
public class ProductoCrearDto {

    // Atributos del producto
    private String codigoProducto;
    private String nombreProducto;
    private String descripcion;
    private Double precio;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;

    // Datos relacionados con la categoria del producto
    @NotNull(message=" idCategoria es obligatorio")
    private Long idCategoria;
    private String nombreCategoria;

    // Datos relacionados con la marca del producto
    @NotNull(message=" marca es obligatorio")
    private Long idMarca;
    private String nombreMarca;

    // Lista de imagenes asociadas al producto
    private List<ImagenCrearDto> imagenesProducto;



    // Métodos getter y setter para acceder y modificar atributos

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
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

    public List<ImagenCrearDto> getImagenesProducto() {
        return imagenesProducto;
    }

    public void setImagenesProducto(List<ImagenCrearDto> imagenesProducto) {
        this.imagenesProducto = imagenesProducto;
    }
}
