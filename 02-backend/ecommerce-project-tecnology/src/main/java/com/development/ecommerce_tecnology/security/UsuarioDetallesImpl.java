package com.development.ecommerce_tecnology.security;

// Importaciones de Spring Security
import org.springframework.security.core.GrantedAuthority; // Representa un rol o autoridad de un usuario
import org.springframework.security.core.userdetails.UserDetails; // Intefaz que Spring security usa para manejar usuarios

import java.util.Collection;

// Esta clase implementa UserDetailsm que es la interfaz que Spring Security usa para manejar la autenticación y autorización
// Aqui definimos los detalles del usuario que serán utilizados por el framework
public class UsuarioDetallesImpl implements UserDetails {


    private Long idUsuario;
    private String nombreUsuario;
    private String contrasenia;
    // Colección de roles o permisos del usuario
    // Cada GrantedAuthority representa un rol como "ROLE_ADMIN" o "ROLE_USER"
    private Collection<? extends GrantedAuthority>authorities;

    // Constructor que inicializa todos los campos de clase
    public UsuarioDetallesImpl(Long idUsuario, String nombreUsuario,String contrasenia,
                               Collection<? extends GrantedAuthority> authorities) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.authorities = authorities;
    }


    // Retorna el Id del usuario
    public Long getIdUsuario() {
        return idUsuario;
    }

    // _______________________________________
    //  Métodos de UderDetails (obligatorios)
    // _______________________________________

    // Retorna nombreUsuario
    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    // Retorna contrasenia
    @Override
    public String getPassword() {
        return contrasenia;
    }


    // Retorna los roles o autoridades de usuario
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // Indica si la cuenta del usuario no ha expirado
    @Override
    public boolean isAccountNonExpired() {
        return true; // true indica que la cuenta sigue activa
    }

    // Indica si la cuenta de usuario no está bloqueada
    @Override
    public boolean isAccountNonLocked() {
        return true; // true indica que la cuenta no ésta bloqueda
    }

    // indica si las credenciales (contraseña) no han expirado
    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // true indica que la contraseña sigue válida
    }

    // Indica si la cuenta está habilitada
    @Override
    public boolean isEnabled() {
        return true; // true indica que el usuario está activo y puede inciar sesión

    }
}
