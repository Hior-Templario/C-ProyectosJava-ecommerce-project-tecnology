package com.development.ecommerce_tecnology.dto;


public class MarcaCrearDto {

    // Datos relacionados a la marca
    private String nombreMarca;
    private String descripcion;

    // Imagen asociada a la marca
    private ImagenCrearDto imagenMarca;

    // Métodos getter y setter para acceder y modificar atributos


    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ImagenCrearDto getImagenMarca() {
        return imagenMarca;
    }

    public void setImagenMarca(ImagenCrearDto imagenMarca) {
        this.imagenMarca = imagenMarca;
    }
}
