package com.development.ecommerce_tecnology.dao;

import com.development.ecommerce_tecnology.entity.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(collectionResourceRel = "movimientoInventario", path = "movimientoInventario" )
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario , Long> {


}
