package com.development.ecommerce_tecnology.dto;
import com.development.ecommerce_tecnology.entity.Categoria;

import java.util.List;

public class CategoriaDto {

    private Long idCategoria;
    private String nombreCategoria;
    private String descripcion;
    private String prefijoCategoria;
    private List<ImagenDto> imagenesCategoria;

    public CategoriaDto (Categoria categoria , List<ImagenDto> imagenesCategoriaDto){

        this.idCategoria = categoria.getIdCategoria();
        this.nombreCategoria = categoria.getNombreCategoria();
        this.descripcion = categoria.getDescripcion();
        this.prefijoCategoria=categoria.getPrefijoCategoria();
        this.imagenesCategoria = imagenesCategoriaDto;

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

    public List<ImagenDto> getImagenesCategoria() {
        return imagenesCategoria;
    }

    public void setImagenesCategoria(List<ImagenDto> imagenesCategoria) {
        this.imagenesCategoria = imagenesCategoria;
    }
}
