package com.development.ecommerce_tecnology.dao;

import com.development.ecommerce_tecnology.entity.Imagen;
import com.development.ecommerce_tecnology.enums.TipoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {


    // --- Métodos de busqueda ---
    List<Imagen> findByTipoAndIdEntidad(TipoEntidad tipo, Long idEntidad);
    List<Imagen> findByTipoAndIdEntidadIn(TipoEntidad tipo, List<Long> idEntidad);
    Imagen findFirstByTipoAndIdEntidad(TipoEntidad tipo, Long idEntidad);
    List<Imagen> findByIdEntidad(Long idEntidad);

    // --- Métodos de eliminación ---
    @Transactional
    @Modifying
    void  deleteByUrlImagen (String urlImagen);

    @Transactional
    @Modifying
    void  deleteByTipoAndIdEntidad(TipoEntidad tipo, Long idEntidad);

}
