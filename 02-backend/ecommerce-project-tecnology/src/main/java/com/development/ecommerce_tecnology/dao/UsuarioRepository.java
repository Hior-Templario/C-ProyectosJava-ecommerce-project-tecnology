package com.development.ecommerce_tecnology.dao;

import com.development.ecommerce_tecnology.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


}
