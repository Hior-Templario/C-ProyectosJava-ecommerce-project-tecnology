package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dto.ImagenCrearDto;
import com.development.ecommerce_tecnology.entity.Imagen;
import com.development.ecommerce_tecnology.enums.TipoEntidad;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface ImagenService {

    void subirImagenPorEntidad(
            ImagenCrearDto imagenes,
            TipoEntidad tipoEntidad,
            Long idEntidad
    ) throws IOException;

    void subirImagenesPorEntidad(
            List<ImagenCrearDto> imagenes,
            TipoEntidad tipoEntidad,
            Long idEntidad
    ) throws IOException;

    void eliminarImagenesPorEntidad(
            TipoEntidad tipoEntidad,
            Long idEntidad);

    Imagen obtenerImagenPrincipalPorEntidad(
            TipoEntidad tipoEntidad,
            Long idEntidad);


    List<Imagen> obtenerImagenesDeEntidad(
            TipoEntidad tipoEntidad,
            Long idEntidad);

    Map<Long, List<Imagen>>obtenerImagenesDeEntidades(
            TipoEntidad tipoEntidad,
            List<Long> idsEntidad
    );
}
