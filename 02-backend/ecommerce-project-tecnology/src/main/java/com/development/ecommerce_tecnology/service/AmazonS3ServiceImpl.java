package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dao.ImagenRepository;
import com.development.ecommerce_tecnology.entity.Imagen;
import com.development.ecommerce_tecnology.enums.TipoEntidad;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {

    private final S3Client s3Client;
    private final ImagenRepository imagenRepository;

    @Value("${aws.bucketName}")
    private String bucketName;

    public AmazonS3ServiceImpl(S3Client s3Client, ImagenRepository imagenRepository) {
        this.s3Client = s3Client;
        this.imagenRepository = imagenRepository;
    }

    @Override
    public String subirArchivo(MultipartFile archivo, String nombreArchivo , TipoEntidad tipo, Long idEntidad) throws IOException {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(nombreArchivo)
                .contentType(archivo.getContentType())
                .build();

        s3Client.putObject(request, RequestBody.fromInputStream(archivo.getInputStream(), archivo.getSize()));

        String url = "https://" + bucketName + ".s3.amazonaws.com/" + nombreArchivo;

        Imagen imagen = new Imagen();
        imagen.setUrlImagen(url);
        imagen.setTipo(tipo);
        imagen.setIdEntidad(idEntidad);

        imagenRepository.save(imagen);

        return url;

    }

    @Override
    public void eliminarArchivo(String nombreArchivo) {
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(nombreArchivo)
                .build();

        s3Client.deleteObject(deleteRequest);

        String url = "https://" + bucketName + ".s3.amazonaws.com/" + nombreArchivo;
        imagenRepository.deleteByUrlImagen(url);

    }

    @Override
    public void eliminarArchivoPorEntidad(TipoEntidad tipo, Long idEntidad) {

        List<Imagen> imagenes= imagenRepository.findByIdEntidad(idEntidad);

        for (Imagen imagen : imagenes) {
            String nombreArchivo = extraerNombreDeUrl(imagen.getUrlImagen());

            try {
                DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(nombreArchivo)
                        .build();

                s3Client.deleteObject(deleteRequest);
            } catch (Exception e){
                // Puedes loguear el error o notificar según el caso
                System.err.println("Error al eliminar archivo en S3: " + nombreArchivo);
            }
        }

        imagenRepository.deleteByTipoAndIdEntidad(tipo, idEntidad);
    }

    private String extraerNombreDeUrl(String url){
        if (url == null || !url.contains("/")) return "";
        return url.substring(url.lastIndexOf("/") + 1);
    }

}
