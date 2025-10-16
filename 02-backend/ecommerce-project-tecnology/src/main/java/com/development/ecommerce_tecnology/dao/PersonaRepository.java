package com.development.ecommerce_tecnology.dao;

import com.development.ecommerce_tecnology.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "personas", path = "personas" )
public interface PersonaRepository  extends JpaRepository<Persona, Long> {


}
