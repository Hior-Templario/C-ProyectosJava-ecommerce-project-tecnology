package com.development.ecommerce_tecnology.dto;

import com.development.ecommerce_tecnology.entity.Marca;

import java.util.List;

public class MarcaDto {

    private Long idMarca;
    private String nombreMarca;
    private String descripcion;
    private List<ImagenDto> imagenesMarcaDto;

    public MarcaDto(Marca marca, List<ImagenDto> imagenesMarcaDto) {

        this.idMarca = marca.getIdMarca();
        this.nombreMarca = marca.getNombreMarca();
        this.descripcion = marca.getDescripcion();
        this.imagenesMarcaDto = imagenesMarcaDto;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<ImagenDto> getImagenesMarcaDto() {
        return imagenesMarcaDto;
    }

    public void setImagenesMarcaDto(List<ImagenDto> imagenesMarcaDto) {
        this.imagenesMarcaDto = imagenesMarcaDto;
    }
}
