package com.development.ecommerce_tecnology.controller;

// DTO que recibe los datos de autenticación desde el cliente
import com.development.ecommerce_tecnology.dto.SolicitudAutenticacionDto;
// Clase para generar y validar tokens JWT
import com.development.ecommerce_tecnology.security.JwtUtil;
// Implementación personalizada de UserDetails para manejar información de usuario
import com.development.ecommerce_tecnology.security.UsuarioDetallesImpl;
// Servicio que carga los detalles de usuario (implementación de UserDetailsService)
import com.development.ecommerce_tecnology.service.UsuarioDetallesServiceImpl;

// Clases de Spring para manejo de respuesta  HTTP
import org.springframework.http.ResponseEntity;
// Clases de Spring security para autenticación
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// Para manejar transacciones
import org.springframework.transaction.annotation.Transactional;
// Anotaciones para definir controladores y endpoints REST
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

// Se indica que esta clase será un controlador REST, que responderá con JSON u objetos serializados
@RestController
// Se define la ruta base para todos los endpoints de este controlador: /api
@RequestMapping("/api")
public class AutenticacionController {

    // Se inyecta el gestor de autenticación de Spring Security
    private final AuthenticationManager authenticationManager;
    // Servicio personalizado que carga los detalles de usuario (implementación de UserDetailsService)
    private final UsuarioDetallesServiceImpl usuarioDetallesServicio;
    // Utilidad para generar y validar tokens JWT
    private final JwtUtil jwtUtil;

    // Constructor con inyección de dependencias (Spring lo gestiona automáticamente)
    public AutenticacionController(AuthenticationManager authenticationManager, UsuarioDetallesServiceImpl usuarioDetallesServicio, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.usuarioDetallesServicio = usuarioDetallesServicio;
        this.jwtUtil = jwtUtil;
    }

    // Indica que este método debe ejecutarse dentro de una transacción
    @Transactional
    // Define el endpoint POST /api/login
    @PostMapping("/login")
    // Respuesta genérica, ya que puede devolver un token o un error
    public ResponseEntity<?> autenticar(@RequestBody SolicitudAutenticacionDto solicitud) {

        try {

            // 1. Autenticar credenciales con Spring Security
            // Si las credenciales son inválidas, lanzará una excepción
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            solicitud.getNombreUsuario(), // Usuario enviado en la solicitud
                            solicitud.getContrasenia()) // contraseña enviada en la solicitud
            );


            // 2. Cargar los datos del usuario desde la base de datos
            // Aquí se usa la implementación propia de UserDetails (UsuarioDetallesImpl)
            UsuarioDetallesImpl usuarioDetalles =
                    (UsuarioDetallesImpl)usuarioDetallesServicio.loadUserByUsername(solicitud.getNombreUsuario());


            // 3. Obtener los roles/autorizaciones del usuario
            // Se convierten de GrantedAuthority a una lista de String
            List<String> roles = usuarioDetalles.getAuthorities()
                    .stream()
                    .map(authority -> authority.getAuthority()) // Extrae el nombre del rol
                    .toList();

            // 4. Generar un token JWT con el nombre de usuario y sus roles
            String token = jwtUtil.generarToken(
                            usuarioDetalles.getUsername(), // Nombre de usuario
                            Map.of("roles", roles) // Se añade roles al payload del token
            );

            // 5. Construir la respuesta con token y datos adicionales del usuario
            Map<String, Object> respuesta = Map.of(
                    "token", token,                              // token JWT generado
                    "idUsuario", usuarioDetalles.getIdUsuario(),  // Id único del usuario
                    "usuario", usuarioDetalles.getUsername(),     // Nombre de usuario
                    "roles",roles                                 // Lista de roles
            );

            // Se devuelve la respuesta con código 200 OK
            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            // Si ocurre cualquier excepción (ej: credenciales inválidas),
            // se devuelve un error 401 Unauthorized con un mensaje
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
        }
    }
}