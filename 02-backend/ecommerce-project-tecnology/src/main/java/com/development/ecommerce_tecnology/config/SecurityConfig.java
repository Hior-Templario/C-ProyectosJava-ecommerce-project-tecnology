package com.development.ecommerce_tecnology.config;


import com.development.ecommerce_tecnology.service.UsuarioDetallesServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration // Indica qu esta clasee es una configuracion de Sprinboot
@EnableWebSecurity // Habilita la configuración de seguridad de Spring Securuty
public class SecurityConfig {

    // Servicio persolanizado para cargar los detalles del usuario

    private final UsuarioDetallesServiceImpl usuarioDetallesServicioImpl;

    // Inyecciòn de dependencia del servicio autenticacion
    public SecurityConfig(UsuarioDetallesServiceImpl usuarioDetallesServicioImpl) {
        this.usuarioDetallesServicioImpl = usuarioDetallesServicioImpl;
    }

    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws    Exception {
        // Configura la seguridad de las rutas y autentticacion.

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Permite el acceso sin autenticaaciòn a la pagina inicio, login,css,js
                        .requestMatchers(
                                        "/", "/api/login","/css/**","/js/**","/api/productos/**","/api/usuarios/**","/api/categorias/**", "/api/marcas/**", "/api/estadosUsuario/**",
                                "/api/movimientoInventario/**" , "/api/registrarMovimiento/**", "/api/crear/**" , "/api/roles/**" , "/api/imagenes/**"
                                ).permitAll()
                        // Solo permite acceso a la lista de usuarios a SOPORTE Y ADMIN
                        .requestMatchers("/usuarios").hasAnyRole("ADMIN","SOPORTE")
                        // Solo los usuarios con rol ADMIN pueden acceder a las rutas  bajo "/admin/**"
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // cualquier otra solicitud requiere autenticaciòn
                        .anyRequest().authenticated()
                )
                .userDetailsService(usuarioDetallesServicioImpl)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

//                .formLogin(form -> form
//                        .loginPage("/login") // Pàgina de inicio de sesiòn personalizado
//                        .defaultSuccessUrl("/" , true) // Redirige al inicio despùes de iniciar sesiòn exitosamente
//                        .failureUrl("/login?error=true") // Redirige a la pàgina de login con mensaje de error si falla autenticaciòn
//                        .permitAll() // Permite acceso a la pagina de login
//                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout") // Redirige a la pàgina de login tras cerrar sesiòn
                        .invalidateHttpSession(true) // Invalida la sesiòn al cerrar sesiòn
                        .deleteCookies("JSESSIONID") // Borrar la cokkie la sesiòn
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of(
                "http://localhost:4200",
                "https://proyectos-angular-ecommerce-project.vercel.app",
                "https://*.vercel.app"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return source;
    }

    // Defin un codificador de contraseñas usando BCrypt, lo que proporciiona seguridad en el almacenamiento de contraseñas.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Devuelve el AuthenticationManager para manejar la autentificacion de usuariod
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
