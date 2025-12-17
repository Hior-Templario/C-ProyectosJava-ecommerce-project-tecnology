package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.enums.TipoEntidad;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AmazonS3Service {

    String subirArchivo(MultipartFile archivo , TipoEntidad tipo, Long idEntidad)throws IOException ;

    void eliminarArchivoPorKey(String s3Key);

    void eliminarArchivoPorEntidad(TipoEntidad tipo, Long idEntidad);


}
