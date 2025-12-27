package com.development.ecommerce_tecnology.controller;

import com.development.ecommerce_tecnology.dto.*;
import com.development.ecommerce_tecnology.entity.EstadoUsuario;
import com.development.ecommerce_tecnology.enums.TipoEntidad;
import com.development.ecommerce_tecnology.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService =usuarioService;
    }

//    @GetMapping
//    public ResponseEntity<List<UsuarioDto>>obtenerTodos(){
//        return ResponseEntity.ok(usuarioService.obtenerTodosUsuariosConImagen());
//    }


    @GetMapping("/usuariosPaginados")
    public ResponseEntity<Page<UsuarioDto>>listarUsuariossPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size){

        Pageable pageable = PageRequest.of(page, size , Sort.by("nombreUsuario").ascending());
        Page<UsuarioDto>usuarios= usuarioService.obtenerTodosUsuariosConImagen(pageable);

        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/usuariosPersonasPaginados")
    public ResponseEntity<Page<UsuarioPersonaDto>>obtenerTodasPersonas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size){
            Pageable pageable = PageRequest.of(page, size , Sort.by("nombreUsuario").ascending());
            Page<UsuarioPersonaDto>usuariosPersonas= usuarioService.obtenerTodosUsuariosPersonasConImagen(pageable);

            return ResponseEntity.ok(usuariosPersonas);
    }

    @GetMapping("/perfilUsuario/{idUsuario}")
    public ResponseEntity<UsuarioDto> obtenerUsuarioPorId(@PathVariable Long idUsuario){
        return ResponseEntity.ok(usuarioService.obtenerUsuarioConImagen(idUsuario));
    }

    @GetMapping("/perfilUsuarioPersona/{idUsuario}")
    public ResponseEntity<UsuarioPersonaDto> obtenerUsuarioPersonaPorId(@PathVariable Long idUsuario){
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPersonaConImagen(idUsuario));
    }

    @PostMapping(value = "/crearUsuario", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UsuarioDto> crearUsuario(

            @RequestParam("nombreUsuario") String nombreUsuario,
            @RequestParam("correo") String correo,
            @RequestParam("contrasenia") String contrasenia,
            @RequestParam("fechaRegistro") LocalDateTime fechaRegistro,
            @RequestParam("idRol") Long idRol,
            @RequestParam("nombreRol") String nombreRol,
            @RequestParam("idEstado") Long idEstado,
            @RequestParam("nombreEstado") String nombreEstado,
            @RequestParam("descripcionEstado") String descripcionEstado,
            @RequestParam(value = "archivo", required = false)MultipartFile archivo,
            @RequestParam(value = "tipo", required = false)TipoEntidad tipo
            )throws IOException {

        UsuarioCrearDto usuarioCrearDto =new UsuarioCrearDto();
        usuarioCrearDto.setNombreUsuario(nombreUsuario);
        usuarioCrearDto.setCorreo(correo);
        usuarioCrearDto.setContrasenia(contrasenia);
        usuarioCrearDto.setFechaRegistro(fechaRegistro);
        usuarioCrearDto.setIdRol(idRol);
        usuarioCrearDto.setNombreRol(nombreRol);
        usuarioCrearDto.setIdEstado(idEstado);
        usuarioCrearDto.setNombreEstado(nombreEstado);
        usuarioCrearDto.setDescripcionEstado(descripcionEstado);

        if (archivo != null && !archivo.isEmpty()){
            ImagenCrearDto imagen = new ImagenCrearDto();
            imagen.setArchivo(archivo);
            imagen.setTipo(tipo != null ? tipo : TipoEntidad.USUARIO);
            usuarioCrearDto.setImagenUsuario(imagen);
        }

        UsuarioDto usuarioGuardado = usuarioService.crearUsuarioConImagen(usuarioCrearDto);


        return new ResponseEntity<>(usuarioGuardado, HttpStatus.CREATED);
    }


    @PostMapping(value = "/crearUsuarioPersona", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UsuarioPersonaDto> crearUsuarioPersona(

            @RequestParam("nombreUsuario") String nombreUsuario,
            @RequestParam("correo") String correo,
            @RequestParam("contrasenia") String contrasenia,
            @RequestParam( value = "fechaRegistro" , required = false) LocalDateTime fechaRegistro,

            @RequestParam("idRol") Long idRol,
            @RequestParam("nombreRol") String nombreRol,

            @RequestParam("idEstado") Long idEstado,
            @RequestParam("nombreEstado") String nombreEstado,
            @RequestParam( value ="descripcionEstado" ,required = false) String descripcionEstado,

            @RequestParam("nombresPersona") String nombresPersona,
            @RequestParam("apellidosPersona") String apellidosPersona,
            @RequestParam("tipoDocumento") String tipoDocumento,
            @RequestParam("numeroDocumento") String numeroDocumento,
            @RequestParam("fechaNacimiento") LocalDate fechaNacimiento,
            @RequestParam("sexo") String sexo,
            @RequestParam("telefono") String telefono,
            @RequestParam("correoSecundario") String correoSecundario,
            @RequestParam("direccion") String direccion,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("pais") String pais,

            @RequestParam(value = "archivo", required = false)MultipartFile archivo,
            @RequestParam(value = "tipo", required = false)TipoEntidad tipo
    )throws IOException {

        if (fechaRegistro == null){
            fechaRegistro = LocalDateTime.now();
        }

        if (descripcionEstado == null){
            descripcionEstado = "Usuario con acceso habilitado";
        }

        UsuarioPersonaCrearDto usuarioPersonaCrearDto =new UsuarioPersonaCrearDto();

        usuarioPersonaCrearDto.setNombreUsuario(nombreUsuario);
        usuarioPersonaCrearDto.setCorreo(correo);
        usuarioPersonaCrearDto.setContrasenia(contrasenia);
        usuarioPersonaCrearDto.setFechaRegistro(fechaRegistro);

        usuarioPersonaCrearDto.setIdRol(idRol);
        usuarioPersonaCrearDto.setNombreRol(nombreRol);

        usuarioPersonaCrearDto.setIdEstado(idEstado);
        usuarioPersonaCrearDto.setNombreEstado(nombreEstado);
        usuarioPersonaCrearDto.setDescripcionEstado(descripcionEstado);

        PersonaDto persona = new PersonaDto();
        persona.setNombresPersona(nombresPersona);
        persona.setApellidosPersona(apellidosPersona);
        persona.setTipoDocumento(tipoDocumento);
        persona.setNumeroDocumento(numeroDocumento);
        persona.setFechaNacimiento(fechaNacimiento);
        persona.setSexo(sexo);
        persona.setTelefono(telefono);
        persona.setCorreoSecundario(correoSecundario);
        persona.setDireccion(direccion);
        persona.setCiudad(ciudad);
        persona.setPais(pais);

        usuarioPersonaCrearDto.setPersonaDto(persona);

        if (archivo != null && !archivo.isEmpty()){
            ImagenCrearDto imagen = new ImagenCrearDto();
            imagen.setArchivo(archivo);
            imagen.setTipo(tipo != null ? tipo : TipoEntidad.USUARIO);
            usuarioPersonaCrearDto.setImagenUsuario(imagen);
        }


        UsuarioPersonaDto usuarioGuardado = usuarioService.crearUsuarioPersonaConImagen(usuarioPersonaCrearDto);


        return new ResponseEntity<>(usuarioGuardado, HttpStatus.CREATED);
    }

}
