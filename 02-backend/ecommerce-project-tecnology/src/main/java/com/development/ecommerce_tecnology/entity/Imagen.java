package com.development.ecommerce_tecnology.entity;

import com.development.ecommerce_tecnology.enums.TipoEntidad;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="imagen")
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen")
    private Long idImagen;

    @Column(name = "url_imagen")
    private String urlImagen;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo" , nullable = false)
    private TipoEntidad tipo;

    @Column(name = "id_entidad")
    private Long idEntidad;


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

    public TipoEntidad getTipo() {
        return tipo;
    }

    public void setTipo(TipoEntidad tipo) {
        this.tipo = tipo;
    }

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }
}


