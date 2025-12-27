package com.development.ecommerce_tecnology.dto;

import com.development.ecommerce_tecnology.enums.TipoEntidad;
import org.springframework.web.multipart.MultipartFile;

public class ImagenCrearDto {

    private MultipartFile archivo;
    private TipoEntidad tipo;


    public ImagenCrearDto() {
    }

    public ImagenCrearDto(MultipartFile archivo, TipoEntidad tipo) {
        this.archivo = archivo;
        this.tipo = tipo;
    }

    public MultipartFile getArchivo() {
        return archivo;
    }

    public void setArchivo(MultipartFile archivo) {
        this.archivo = archivo;
    }

    public TipoEntidad getTipo() {
        return tipo;
    }

    public void setTipo(TipoEntidad tipo) {
        this.tipo = tipo;
    }


}
