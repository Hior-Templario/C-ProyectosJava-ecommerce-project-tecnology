//  Paquete en el que está esta clase de configuración
package com.development.ecommerce_tecnology.config;

// Indica que esta clase es una clase dec onfiguración de Spring
import org.springframework.context.annotation.Configuration;

// Permite configurar reglas de CORS para los controladores REST
import org.springframework.web.servlet.config.annotation.CorsRegistry;

// Interfaz que se puede implementar para personalizar la configuración del MVC de Spring
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Marca esta clase como una clase de configuración de Spring
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // Sobreescribe el método para agregar configuraciones CORS personalizados
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica la configuración a todas las rutas backend
                .allowedOrigins("http://localhost:4200") // Permite solicitudes desde este origen (Angular en modo desarrollo)
                .allowedMethods("*"); // Permite todos los métodos HTTP (GET, POST, PUT, DELETE, etc.)
    }
}
