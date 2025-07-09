// Paquete donde esta ubicada la clase
package com.development.ecommerce_tecnology.config;

// Proporciona acceso al contexto de persistencia JPA
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;

// Anotaciones y utilidades de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

// Configuración de Spring  Data REST (para exponer repositorios como endpoints REST automatico)
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

// Permite configurar reglas de CORS para los endpoints REST
import org.springframework.web.servlet.config.annotation.CorsRegistry;

// Clase  utilitarias para trabajar con listas y conjuntos
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration // Marca esta clase como una clase de configuración de Spring
public class MyDataRestConfig implements RepositoryRestConfigurer {

// EntityManager se usa para acceder al metamodelo de entidades JPA
private final EntityManager entityManager;

    // Constructor que inyecta el EntityManager
    @Autowired
    public MyDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Método sobrescrito para configurar Spring Data Rest
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        // Llama la implementación por defecto del método
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        // Llama el método personalizado para exponer Los IDs
        exposeIds(config);
    }

    // Método para exponer los IDs de todas las entidades JPA en las respuestas REST
    private void exposeIds(RepositoryRestConfiguration config){

        // Obtiene rodas las entidades del metamodelo JPA
        Set<EntityType<?>> entities=entityManager.getMetamodel().getEntities();

        // Crea una lista para almacenar las clases de las entidades
        List<Class> entityClasses =new ArrayList<>();

        // Recorre cada entidad y obtiene su tipo Java
        for(EntityType<?> tempEntityType : entities){
            entityClasses.add(tempEntityType.getJavaType());
        }

        // Convierte la lista a un areglo
        Class[] domainTypes = entityClasses.toArray(new Class[0]);

        // Expone los IDs para cada tipo de entidad (esto hace que aparezcan los ID en el JSON del Rest)
        config.exposeIdsFor(domainTypes);
    }

}
