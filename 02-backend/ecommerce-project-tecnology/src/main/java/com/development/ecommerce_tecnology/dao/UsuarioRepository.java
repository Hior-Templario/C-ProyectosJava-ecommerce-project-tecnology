package com.development.ecommerce_tecnology.dao;

import com.development.ecommerce_tecnology.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);
    Usuario findByNombreUsuario(String nombreUsuario);

}
