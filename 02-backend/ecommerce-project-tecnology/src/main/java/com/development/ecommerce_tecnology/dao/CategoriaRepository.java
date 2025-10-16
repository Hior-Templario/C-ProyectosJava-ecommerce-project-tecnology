package com.development.ecommerce_tecnology.dao;

import com.development.ecommerce_tecnology.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT c.prefijoCategoria FROM Categoria c WHERE c.idCategoria = :idCategoria")
    String obtenerPrefijoPorId (@Param("idCategoria") Long idCategoria);

}
