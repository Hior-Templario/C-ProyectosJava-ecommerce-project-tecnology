// Paquete donde se encuentra esta clase de configuración
package com.development.ecommerce_tecnology.config;

// Anotación para inyectar valores desde el archivo de configuración (application.proporties o .yml)
import org.springframework.beans.factory.annotation.Value;

// Anotación para declarar métodos como @Bean dentro de una clase de configuración
import org.springframework.context.annotation.Bean;

// Anotación para marca la clase como fuente de deficioniones de beans para el contexto de Spring
import org.springframework.context.annotation.Configuration;

// Librerías de SDK de AWS v2 para manejar las credenciales basicas
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

// Cliente del servicio Amazon S3 (para subir, eliminar, leer archivos en S3)
import software.amazon.awssdk.services.s3.S3Client;

// Clase para definir la región en la que operará el cliente S3 (como us-east-2)
import software.amazon.awssdk.regions.Region;

// Está anotación le indica a Spring que esta clase contiene configuraciones
@Configuration
public class AmazonS3Config {

    // Se inyectan los valores definidos en application.properties o pplication.yml
    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    // Define un bean de tipo S3Client que será administrado por Spring
    @Bean
    public S3Client s3Client(){
        // Se crean las credenciales básicas usando la accesskey y secretKey
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        // Se construye y retorma el cliente  S3 configurando con la región y las credenciales
        return S3Client.builder()
                // Establece la región (puedes usar Region.of(region) si quieres que sea dinámica)
                .region(Region.of(region))

                // Se le asignan las credenciales al cliete
                .credentialsProvider(StaticCredentialsProvider.create(credentials))

                // Construye la instancia final del cliete S3
                .build();
    }
}
