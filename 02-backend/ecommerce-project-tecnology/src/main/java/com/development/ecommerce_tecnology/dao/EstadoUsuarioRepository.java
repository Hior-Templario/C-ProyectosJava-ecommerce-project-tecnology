package com.development.ecommerce_tecnology.dao;

import com.development.ecommerce_tecnology.entity.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "estadosUsuario", path = "estadosUsuario" )
public interface EstadoUsuarioRepository extends JpaRepository<EstadoUsuario, Long> {
}
