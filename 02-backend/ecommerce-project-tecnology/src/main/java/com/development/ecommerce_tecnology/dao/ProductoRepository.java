package com.development.ecommerce_tecnology.dao;

import com.development.ecommerce_tecnology.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Métodos de búsqueda
    List<Producto> findByCategoria_IdCategoria(Long idCategoria);
    List<Producto> findByMarca_IdMarca(Long idMarca);
    List<Producto> findByCodigoProductoContainingIgnoreCaseOrNombreProductoContainingIgnoreCase(
            String codigoProducto ,String nombreProducto);


    // Métodos de actualización








    // Devuelve una pagina de productos (Spring ya maneja LIMIT/OFFSET en SQL)

    // __ Contar productos para incrementar codigo producto
    @Query("SELECT COUNT(p) FROM Producto p WHERE p.categoria.idCategoria = :idCategoria")
    Long contarPorCategoria(@Param("idCategoria")Long idCategoria);


}

