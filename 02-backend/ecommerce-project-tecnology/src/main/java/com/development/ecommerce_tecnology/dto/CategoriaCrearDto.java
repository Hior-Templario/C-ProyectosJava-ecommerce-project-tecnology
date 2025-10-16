package com.development.ecommerce_tecnology.dto;

public class CategoriaCrearDto {

    // Datos relacionados con la categoria
    private String nombreCategoria;
    private String descripcion;
    private String prefijoCategoria;

    // Imagen asociada a la categoria

    private  ImagenCrearDto imagenCategoria;

    // Métodos getter y setter para acceder y modificar atributos


    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrefijoCategoria() {
        return prefijoCategoria;
    }

    public void setPrefijoCategoria(String prefijoCategoria) {
        this.prefijoCategoria = prefijoCategoria;
    }

    public ImagenCrearDto getImagenCategoria() {
        return imagenCategoria;
    }

    public void setImagenCategoria(ImagenCrearDto imagenCategoria) {
        this.imagenCategoria = imagenCategoria;
    }
}
