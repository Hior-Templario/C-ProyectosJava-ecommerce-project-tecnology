package com.development.ecommerce_tecnology.dao;

import com.development.ecommerce_tecnology.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {



}
