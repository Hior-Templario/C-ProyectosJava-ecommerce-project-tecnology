package com.development.ecommerce_tecnology.dao;

import com.development.ecommerce_tecnology.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // --- Métodos de búsqueda ---
    List<Producto> findByCategoria_IdCategoria(Long idCategoria);
    List<Producto> findByMarca_IdMarca(Long idMarca);

}

