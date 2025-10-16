package com.development.ecommerce_tecnology.service;

// Repositorio para acceder a la BD
import com.development.ecommerce_tecnology.dao.UsuarioRepository;
// Entidad Usuario
import com.development.ecommerce_tecnology.entity.Usuario;
// Implementación de UserDetails personalizada
import com.development.ecommerce_tecnology.security.UsuarioDetallesImpl;
// Representa un rol/ autoridad simple
import org.springframework.security.core.authority.SimpleGrantedAuthority;
// Interfaz que representa un usuario para Spring Security
import org.springframework.security.core.userdetails.UserDetails;
// Interfaz para cargar usuarios desde DB
import org.springframework.security.core.userdetails.UserDetailsService;
// Excepción cuando el usuario no existe
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// Marca esta clase como un servicio Spring
import org.springframework.stereotype.Service;

import java.util.List;

// Se marca la clase como un **Sevicio de Spring**, para inyeccion de dependencias
@Service
public class UsuarioDetallesServiceImpl implements UserDetailsService {

    // Repositorio para acceder a la tabla usuarios en la base de datos
    private final UsuarioRepository usuarioRepository;

    // Constructor para la inyección de dependendias
    public UsuarioDetallesServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    // Metodo principal obligatorio de UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        // Busca el susario pro nombre en la base de datos
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);

        // Si no encuentra el susuario, lanza excepción  que Spring Security captura
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        // Imprime infromación del usuario y consola (Útil para depuración)
        System.out.println("Usuario dentro de loadUserByUsername: " + usuario.getNombreUsuario());
        System.out.println("Contraseña almacenada en BD (BCrypt): " + usuario.getContrasenia());
        System.out.println("Rol del usuario: " + usuario.getRol().getNombreRol());

        // Construye y devuelve un objeto UserDetails personalizado
        // Spring Security usará este objeto para autenticar y autorizar al usuario
        return new UsuarioDetallesImpl(
                usuario.getIdUsuario(), // ID del usuario
                usuario.getNombreUsuario(), // Nombre de usuario
                usuario.getContrasenia(), // Contraseña escriptada
                // Lista de roles del usuario convertidos a GrantedAuthority
                List.of(new SimpleGrantedAuthority(usuario.getRol().getNombreRol()))
        );


    }
}
