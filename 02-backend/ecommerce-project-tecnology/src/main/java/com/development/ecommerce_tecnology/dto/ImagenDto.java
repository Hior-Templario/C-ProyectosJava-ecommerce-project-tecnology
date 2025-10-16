package com.development.ecommerce_tecnology.dto;

import com.development.ecommerce_tecnology.entity.Imagen;
import com.development.ecommerce_tecnology.enums.TipoEntidad;

public class ImagenDto {

    private Long idImagen;
    private String urlImagen;
    private TipoEntidad tipo;
    private Long idEntidad;

    public ImagenDto() {
    }

    public ImagenDto(Imagen imagen) {
        this.idImagen = imagen.getIdImagen();
        this.urlImagen = imagen.getUrlImagen();
        this.tipo = imagen.getTipo();
        this.idEntidad = imagen.getIdEntidad();
    }

    public ImagenDto(Long idImagen, String urlImagen, TipoEntidad tipo, Long idEntidad) {
        this.idImagen = idImagen;
        this.urlImagen = urlImagen;
        this.tipo = tipo;
        this.idEntidad = idEntidad;
    }


    public Long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Long idImagen) {
        this.idImagen = idImagen;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public TipoEntidad getTipo() {
        return tipo;
    }

    public void setTipo(TipoEntidad tipo) {
        this.tipo = tipo;
    }
}
