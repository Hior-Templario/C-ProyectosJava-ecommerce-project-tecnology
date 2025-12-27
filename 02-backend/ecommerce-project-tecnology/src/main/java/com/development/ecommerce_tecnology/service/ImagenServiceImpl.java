package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dao.ImagenRepository;
import com.development.ecommerce_tecnology.dto.ImagenCrearDto;
import com.development.ecommerce_tecnology.entity.Imagen;
import com.development.ecommerce_tecnology.enums.TipoEntidad;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImagenServiceImpl implements ImagenService{

    // Servicio encargado de la gestión de archivos en Amzon S3
    private final AmazonS3Service amazonS3Service;

    // Repositorio para percistencia de metadatos de imàgenes
    private final ImagenRepository imagenRepository;

    // Constructor para inyecciòn de dependencias
    public ImagenServiceImpl(AmazonS3Service amazonS3Service, ImagenRepository imagenRepository) {
        this.amazonS3Service = amazonS3Service;
        this.imagenRepository = imagenRepository;
    }

    @Override
    @Transactional
    public void subirImagenesPorEntidad(
            List<ImagenCrearDto> imagenes,
            TipoEntidad tipoEntidad,
            Long idEntidad) throws IOException {

        // Validar si no hay imagenes para subir
        if (imagenes == null || imagenes.isEmpty()){
            return;
        }

        // Subir cada imagen asociàndola a la entidad correspondiente
        for (ImagenCrearDto imagen : imagenes){

          if (imagen== null ||
          imagen.getArchivo() == null ||
          imagen.getArchivo().isEmpty()){
              continue;
          }

            amazonS3Service.subirArchivo(
                    imagen.getArchivo(),
                    tipoEntidad,
                    idEntidad
            );
        }
    }

    @Override
    @Transactional
    public void subirImagenPorEntidad(
            ImagenCrearDto imagen,
            TipoEntidad tipoEntidad,
            Long idEntidad) throws IOException {

        // Validar si no hay imagenes para subir
        if (imagen == null || imagen.getArchivo().isEmpty()){
            return;
        }

        // Subir imagen asociadad a la entidad correspondiente
        amazonS3Service.subirArchivo(
                imagen.getArchivo(),
                tipoEntidad,
                idEntidad
        );
    }


    @Override
    public void eliminarImagenesPorEntidad(
            TipoEntidad tipoEntidad,
            Long idEntidad) {

        // Eliminar todas las imàgenes asociadas a una entidad especifica
        amazonS3Service.eliminarArchivoPorEntidad(tipoEntidad, idEntidad);

    }

    @Override
    @Transactional(readOnly = true)
    public Imagen obtenerImagenPrincipalPorEntidad(
            TipoEntidad tipoEntidad,
            Long idEntidad) {

        // Obtener imàgenes de una entidad especifica (Una sola entidad)
        return imagenRepository.findFirsttByTipoAndIdEntidad(tipoEntidad, idEntidad);
    }

    @Override
    public List<Imagen> obtenerImagenesDeEntidad(TipoEntidad tipoEntidad, Long idEntidad) {
        return imagenRepository.findByTipoAndIdEntidad(tipoEntidad, idEntidad);
    }

    @Override
    public Map<Long, List<Imagen>> obtenerImagenesDeEntidades(
            TipoEntidad tipoEntidad,
            List<Long> idsEntidad) {

        // Obtener imàgenes de mùltiples entidades y agruparlas porn idEntidad
        return imagenRepository
                .findByTipoAndIdEntidadIn(tipoEntidad, idsEntidad)
                .stream()
                .collect(Collectors.groupingBy(Imagen::getIdEntidad));
    }
}
