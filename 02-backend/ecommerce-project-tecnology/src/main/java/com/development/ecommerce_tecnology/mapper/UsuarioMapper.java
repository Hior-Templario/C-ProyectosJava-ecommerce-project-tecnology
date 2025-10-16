package com.development.ecommerce_tecnology.mapper;

import com.development.ecommerce_tecnology.dto.*;
import com.development.ecommerce_tecnology.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class UsuarioMapper {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioMapper.class);

    // Datos usuario
    public UsuarioDto mappearUsuarioDto(Usuario usuario,
                                        Map<Long, Imagen> imagenesUsuarioDto ) {

        // Crear nuevo DTO vacio
     UsuarioDto usuarioDto = new UsuarioDto();

        // Copiar atibutos basicos del usuario
     usuarioDto.setIdUsuario(usuario.getIdUsuario());
     usuarioDto.setNombreUsuario(usuario.getNombreUsuario());
     usuarioDto.setCorreo(usuario.getCorreo());
     usuarioDto.setContrasenia(usuario.getContrasenia());
     usuarioDto.setFechaRegistro(usuario.getFechaRegistro());

     // procesar informacion del rol  si esta presente
     if (usuario.getRol() != null){
         Long idRol = usuario.getRol().getIdRol();
         usuarioDto.setIdRol(idRol);
         usuarioDto.setNombreRol(usuario.getRol().getNombreRol());

         usuarioDto.setIdRol(idRol);
         logger.info("ID Rol desde usuarioMapper DTO:{}", usuarioDto.getIdRol());
     }
     else {
         // Log de advertencia si el usuaro no tiene Rol
         logger.info("ID Rol desde usuarioMapper no lleego");
     }

        // procesar informacion del rol  si esta presente
        if (usuario.getEstadoUsuario() != null){
            Long idEstado = usuario.getEstadoUsuario().getIdEstado();
            usuarioDto.setIdEstado(idEstado);
            usuarioDto.setNombreEstado(usuario.getEstadoUsuario().getNombreEstado());
            usuarioDto.setDescripcionEstado(usuario.getEstadoUsuario().getDescripcion());

            usuarioDto.setIdEstado(idEstado);
            logger.info("ID Rol desde usuarioMapper DTO:{}", usuarioDto.getIdRol());
        }
        else {
            // Log de advertencia si el usuaro no tiene Rol
            logger.info("ID Rol desde usuarioMapper no lleego");
        }


        // Procesar imagen si esta presente en el mapa
     Imagen imagen = imagenesUsuarioDto.get(usuario.getIdUsuario());
     if(imagen !=null){
         usuarioDto.setImagenUsuario(new ImagenDto(imagen));
     }
     else{
         logger.info("No se encontro imagen para el usuario con ID: {}",usuario.getIdUsuario());
     }
     return usuarioDto;
    }


    // Datos usuario y datos personales
    public UsuarioPersonaDto mappearUsuarioPersonaDto(Usuario usuario,
                                        Map<Long, Imagen> imagenesUsuarioDto ) {


        // Crear nuevo DTO vacio
        UsuarioPersonaDto usuarioPersonaDto = new UsuarioPersonaDto();
        // Copiar atibutos basicos del usuario
        usuarioPersonaDto.setIdUsuario(usuario.getIdUsuario());
        usuarioPersonaDto.setNombreUsuario(usuario.getNombreUsuario());
        usuarioPersonaDto.setCorreo(usuario.getCorreo());
        usuarioPersonaDto.setContrasenia(usuario.getContrasenia());
        usuarioPersonaDto.setFechaRegistro(usuario.getFechaRegistro());

        // procesar informacion del rol  si esta presente
        if (usuario.getRol() != null){
            Long idRol = usuario.getRol().getIdRol();
            usuarioPersonaDto.setIdRol(idRol);
            usuarioPersonaDto.setNombreRol(usuario.getRol().getNombreRol());

            usuarioPersonaDto.setIdRol(idRol);
            logger.info("ID Rol desde usuarioMapper DTO:{}", usuarioPersonaDto.getIdRol());
        }
        else {
            // Log de advertencia si el usuaro no tiene Rol
            logger.info("ID Rol desde usuarioMapper no lleego");
        }

        // procesar informacion del estado  si esta presente
        if (usuario.getEstadoUsuario() != null){
            Long idEstado = usuario.getEstadoUsuario().getIdEstado();
            usuarioPersonaDto.setIdEstado(idEstado);
            usuarioPersonaDto.setNombreEstado(usuario.getEstadoUsuario().getNombreEstado());

            usuarioPersonaDto.setIdEstado(idEstado);
            logger.info("ID Estado desde usuarioMapper DTO:{}", usuarioPersonaDto.getIdEstado());
        }
        else {
            // Log de advertencia si el usuaro no tiene Rol
            logger.info("ID estado desde usuarioMapper no lleego");
        }

        // procesar informacion personal usuario  si esta presente
        if(usuario.getPersona() != null) {
            Long idPersona = usuario.getPersona().getIdpersona();
            usuarioPersonaDto.setIdpersona(usuario.getPersona().getIdpersona());
            usuarioPersonaDto.setNombresPersona(usuario.getPersona().getNombresPersona());
            usuarioPersonaDto.setApellidosPersona(usuario.getPersona().getApellidosPersona());
            usuarioPersonaDto.setTipoDocumento(usuario.getPersona().getTipoDocumento());
            usuarioPersonaDto.setNumeroDocumento(usuario.getPersona().getNumeroDocumento());
            usuarioPersonaDto.setFechaNacimiento(usuario.getPersona().getFechaNacimiento());
            usuarioPersonaDto.setSexo(usuario.getPersona().getSexo());
            usuarioPersonaDto.setTelefono(usuario.getPersona().getTelefono());
            usuarioPersonaDto.setCorreoSecundario(usuario.getPersona().getCorreoSecundario());
            usuarioPersonaDto.setDireccion(usuario.getPersona().getDireccion());
            usuarioPersonaDto.setCiudad(usuario.getPersona().getCiudad());
            usuarioPersonaDto.setPais(usuario.getPersona().getPais());

        }

        // Procesar imagen si esta presente en el mapa
        Imagen imagen = imagenesUsuarioDto.get(usuario.getIdUsuario());
        if(imagen !=null){
            usuarioPersonaDto.setImagenUsuario(new ImagenDto(imagen));
        }
        else{
            logger.info("No se encontro imagen para el usuario con ID: {}",usuario.getIdUsuario());
        }
        return usuarioPersonaDto;
    }


    public Usuario mapperarUsuarioCrearDto (UsuarioCrearDto usuarioCrearDto ){
        Usuario usuario = new Usuario();

        usuario.setNombreUsuario(usuario.getNombreUsuario());
        usuario.setCorreo(usuario.getCorreo());
        usuario.setContrasenia(usuario.getContrasenia());
        usuario.setFechaRegistro(usuario.getFechaRegistro());

        if (usuario.getRol() != null){
            Rol rol = new Rol();

            rol.setIdRol(usuario.getRol().getIdRol());
            rol.setNombreRol(usuario.getRol().getNombreRol());
            usuario.setRol(rol);
        }

        if (usuario.getEstadoUsuario() != null){
            EstadoUsuario estado = new EstadoUsuario();

            estado.setIdEstado(usuario.getEstadoUsuario().getIdEstado());
            estado.setNombreEstado(usuario.getEstadoUsuario().getNombreEstado());
            estado.setDescripcion(usuario.getEstadoUsuario().getDescripcion());
            usuario.setEstadoUsuario(estado);
        }

        if (usuarioCrearDto.getImagenUsuario() != null && usuarioCrearDto.getImagenUsuario().getArchivo() !=null){
            Imagen imagen = new Imagen();

            imagen.setTipo(usuarioCrearDto.getImagenUsuario().getTipo());

        }

        return usuario;

    }

    public Usuario mapperarUsuarioPersonaCrearDto (UsuarioPersonaCrearDto usuarioPersonaCrearDto ){
        Usuario usuario = new Usuario();

        usuario.setNombreUsuario(usuarioPersonaCrearDto.getNombreUsuario());
        usuario.setCorreo(usuarioPersonaCrearDto.getCorreo());
        usuario.setContrasenia(usuarioPersonaCrearDto.getContrasenia());
        usuario.setFechaRegistro(usuarioPersonaCrearDto.getFechaRegistro());

        if (usuarioPersonaCrearDto.getIdRol() != null || usuarioPersonaCrearDto.getNombreRol() != null){

                Rol rol = new Rol();
                rol.setIdRol(usuarioPersonaCrearDto.getIdRol());
                rol.setNombreRol(usuarioPersonaCrearDto.getNombreRol());
                usuario.setRol(rol);
        }

        if (usuarioPersonaCrearDto.getIdEstado() != null
                && usuarioPersonaCrearDto.getNombreEstado() != null){

            EstadoUsuario estado = new EstadoUsuario();

            estado.setIdEstado(usuarioPersonaCrearDto.getIdEstado());
            estado.setNombreEstado(usuarioPersonaCrearDto.getNombreEstado());
            estado.setDescripcion(usuarioPersonaCrearDto.getDescripcionEstado());
            usuario.setEstadoUsuario(estado);
        }


        if (usuario.getEstadoUsuario() != null){
            EstadoUsuario estado = new EstadoUsuario();

            estado.setIdEstado(usuario.getEstadoUsuario().getIdEstado());
            estado.setNombreEstado(usuario.getEstadoUsuario().getNombreEstado());
            estado.setDescripcion(usuario.getEstadoUsuario().getDescripcion());
            usuario.setEstadoUsuario(estado);
        }

        if(usuario.getPersona() == null) {
            Persona persona = new Persona();
            var p = usuarioPersonaCrearDto.getPersonaDto();
            persona.setNombresPersona(p.getNombresPersona());
            persona.setApellidosPersona(p.getApellidosPersona());
            persona.setTipoDocumento(p.getTipoDocumento());
            persona.setNumeroDocumento(p.getNumeroDocumento());
            persona.setFechaNacimiento(p.getFechaNacimiento());
            persona.setSexo(p.getSexo());
            persona.setTelefono(p.getTelefono());
            persona.setCorreoSecundario(p.getCorreoSecundario());
            persona.setDireccion(p.getDireccion());
            persona.setCiudad(p.getCiudad());
            persona.setPais(p.getPais());

            usuario.setPersona(persona);
        }

        if (usuarioPersonaCrearDto.getImagenUsuario() != null && usuarioPersonaCrearDto.getImagenUsuario().getArchivo() !=null){
            Imagen imagen = new Imagen();

            imagen.setTipo(usuarioPersonaCrearDto.getImagenUsuario().getTipo());

        }

        return usuario;

    }
}

