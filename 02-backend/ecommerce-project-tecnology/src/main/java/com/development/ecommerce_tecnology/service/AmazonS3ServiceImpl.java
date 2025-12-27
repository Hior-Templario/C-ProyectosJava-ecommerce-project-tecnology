package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dao.ImagenRepository;
import com.development.ecommerce_tecnology.entity.Imagen;
import com.development.ecommerce_tecnology.enums.TipoEntidad;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {

    // Cliente de Amazon S3 para operaciones de subida y eliminaciòn de archivos
    private final S3Client s3Client;

    // Repositorios para persistir la informaciòn de imàgenes en base de datos
    private final ImagenRepository imagenRepository;

    // Nombre del bucket configurado en application.properties / application.yml
    @Value("${aws.bucketName}")
    private String bucketName;

    // Constructor para inyecciòn de dependencias
    public AmazonS3ServiceImpl(S3Client s3Client, ImagenRepository imagenRepository) {
        this.s3Client = s3Client;
        this.imagenRepository = imagenRepository;
    }

    @Override
    public String subirArchivo(MultipartFile archivo, TipoEntidad tipo, Long idEntidad) throws IOException {

        // General nombre ùnico para el archivo en S3
        String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();

        // Construir request de subida a S3
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(nombreArchivo)
                .contentType(archivo.getContentType())
                .build();

        // Subir archivo a S3
        s3Client.putObject(request, RequestBody.fromInputStream(archivo.getInputStream(), archivo.getSize()));

        // Construir URL pùblica del archivo
        String url = "https://" + bucketName + ".s3.amazonaws.com/" + nombreArchivo;

        // Crear entidad Imagen para persistir wn base de datos
        Imagen imagen = new Imagen();
        imagen.setUrlImagen(url);
        imagen.setS3key(nombreArchivo);
        imagen.setTipo(tipo);
        imagen.setIdEntidad(idEntidad);

        // Guardar metadatos de la imagen
        imagenRepository.save(imagen);

        return url;

    }

    @Override
    public void eliminarArchivoPorKey(String s3Key) {

        // Construir request de eliminaciòn en S3
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build();

        // Eliminar archivo del bucket
        s3Client.deleteObject(deleteRequest);

        // Eliminar registro de imagen en base de datos
        imagenRepository.deleteByS3key(s3Key);

    }

    @Override
    @Transactional
    public void eliminarArchivoPorEntidad(TipoEntidad tipo, Long idEntidad) {

        //  Obtener todas las imàgenes asociadas a una entidad especifica
        List<Imagen> imagenes=
                imagenRepository.findByTipoAndIdEntidad(tipo, idEntidad);

        // Si no hay imàgenes , no se realiza ningunguna acciòn
        if (imagenes.isEmpty()){
            return;
        }

        // Eliminar cada archivo de bucket S3
        for (Imagen imagen : imagenes) {

            try {

               DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(imagen.getS3key())
                        .build();

               s3Client.deleteObject(deleteObjectRequest);

            } catch (Exception e){
                // En casode error en S3, se evita romper el flujo completo
                System.err.println("Error al eliminar archivo en S3: " + imagen.getS3key()
                );
            }
        }

        // Eliminar registros de imàgenes asociados a la entidad
        imagenRepository.deleteByTipoAndIdEntidad(tipo, idEntidad);
    }

}
