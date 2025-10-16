package com.development.ecommerce_tecnology.security;

import io.jsonwebtoken.Claims; // Representa los datos (claims) que contiene el JWT
import io.jsonwebtoken.Jwts; // Clase principalpara contruir y analizar JWTs
import io.jsonwebtoken.SignatureAlgorithm; // Enum con los algoritmos de firma disponible
import org.springframework.beans.factory.annotation.Value; // Permite inyectar valores desde aplication.properties
import org.springframework.stereotype.Component; // Indica qie esta clase es un bean de Spring

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Clave secreta usada para firmar el token (se obtiene de application.properties)
    @Value("${jwt.secret}")
    private String secret;

    // Tiempo de expiración del token en millisegundos (también desde application.properties)
    @Value("${jwt.expiration}")
    private long expiration;

    //Genera un token JWT con el nombre de usuario y claims adicionales
    public String generarToken(String nombreUsuario, Map<String, Object> claims) {

        return Jwts.builder()
                .setClaims(claims) // Informacion personalizada
                .setSubject(nombreUsuario) // Usuario asociado al token
                .setIssuedAt(new Date()) //Fecha de creación
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Fecha de expiración
                .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8)) // Firma clave y algoritmo
                .compact(); // Devuelbe el token como String
    }

    // Obtiene el nombre de usuario (subjet) desde el token
    public String obtenerNombreUsuario(String token){
        return obtenerClaim(token, Claims::getSubject);
    }

    // Verifica que el token pertenezca al usuario y no esté expirado
    public boolean validarToken(String token, String nombreUsuario){
        return (nombreUsuario.equals(obtenerNombreUsuario(token)) && !estaExpirado(token));
    }

    // Verifica si el token ha expirado
    private boolean estaExpirado(String token) {
        return obtenerFechaExpiracion(token).before(new Date());
    }

    // Obtiene la fecha de expiración de token
    private Date obtenerFechaExpiracion(String token) {
        return obtenerClaim(token, Claims::getExpiration);
    }

    // Método genérico para obtener un claim especifico
    private <T> T obtenerClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = obtenerTodosLosClaims(token);
                return claimsResolver.apply(claims);

    }

    // Obtiene todos los claims (informaciín interna) del token
    private Claims obtenerTodosLosClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }


}
