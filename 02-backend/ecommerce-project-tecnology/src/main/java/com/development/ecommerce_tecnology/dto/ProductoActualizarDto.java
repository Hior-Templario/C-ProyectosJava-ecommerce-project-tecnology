package com.development.ecommerce_tecnology.dto;

import jakarta.validation.constraints.NotNull;

public class ProductoActualizarDto {

    // Atributos del producto
    private String nombreProducto;
    private String descripcion;
    private Double precio;

    // Datos relacionados con la categoria del producto
    @NotNull(message=" idCategoria es obligatorio")
    private Long idCategoria;

    // Datos relacionados con la marca del producto
    @NotNull(message=" marca es obligatorio")
    private Long idMarca;


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

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria( Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public  Long getIdMarca() {
        return idMarca;
    }

    public void setIdMarca( Long idMarca) {
        this.idMarca = idMarca;
    }

}
