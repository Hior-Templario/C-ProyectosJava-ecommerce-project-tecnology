package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.enums.TipoEntidad;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AmazonS3Service {

    String subirArchivo(MultipartFile archivo, String nombreArchivo , TipoEntidad tipo, Long idEntidad) throws IOException;

    void eliminarArchivo(String nombreArchivo);

    void eliminarArchivoPorEntidad(TipoEntidad tipo, Long idEntidad);
}
