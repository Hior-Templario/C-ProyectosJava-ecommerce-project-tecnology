package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dao.*;
import com.development.ecommerce_tecnology.dto.*;
import com.development.ecommerce_tecnology.entity.*;
import com.development.ecommerce_tecnology.enums.TipoEntidad;
import com.development.ecommerce_tecnology.mapper.UsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

// Indica que esta clase es un servicio de spring y puede ser inyectado como dependencia
@Service
public class UsuarioServiceImpl implements UsuarioService {

    // Logger para registrar eventos importantes o mensajes de depuración
        private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    // Repositorios e inyecciones de dependencias necesarias operar
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final EstadoUsuarioRepository estadoUsuarioRepository;
    private final PersonaRepository personaRepository;
    private final ImagenRepository imagenRepository;
    private final UsuarioMapper usuarioMapper;
    private final AmazonS3Service amazonS3Service;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Constructor para inyectar dependencias
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository, EstadoUsuarioRepository estadoUsuarioRepository, PersonaRepository personaRepository, ImagenRepository imagenRepository, UsuarioMapper usuarioMapper, AmazonS3Service amazonS3Service) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.estadoUsuarioRepository = estadoUsuarioRepository;
        this.personaRepository = personaRepository;

        this.imagenRepository = imagenRepository;
        this.usuarioMapper = usuarioMapper;
        this.amazonS3Service = amazonS3Service;
    }


    // Metodo para obtener un usuario con su imagen asociada
    @Override
    public UsuarioDto obtenerUsuarioConImagen(Long idUsuario) {

        // Buscar usuario por su ID o lanzar excepcion si no existe
        Usuario usuario= usuarioRepository.findById(idUsuario)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Obtener el ID del rol de usuario
        Long idRol = usuario.getRol().getIdRol();

        // Cargar la imagen asociada al usuario
        Imagen imagen = cargarImagen(TipoEntidad.USUARIO, idUsuario);
        Map<Long, Imagen> imagenDeUsuario = new HashMap<>();

        // Si hay imagen, se asocia al mapa con el Id del usuario
        if (imagen != null){
            imagenDeUsuario.put(idUsuario, imagen);
        }

        // Convertir el objeto Usuario a usuarioDto, incluyendo la imagen
        return usuarioMapper.mappearUsuarioDto(usuario, imagenDeUsuario);
    }


    @Override
    public UsuarioPersonaDto obtenerUsuarioPersonaConImagen(Long idUsuario) {

        // Buscar usuario por su ID o lanzar excepcion si no existe
        Usuario usuario= usuarioRepository.findById(idUsuario)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Obtener el ID del rol de usuario
        Long idRol = usuario.getRol().getIdRol();

        // Cargar la imagen asociada al usuario
        Imagen imagen = cargarImagen(TipoEntidad.USUARIO, idUsuario);
        Map<Long, Imagen> imagenDeUsuario = new HashMap<>();

        // Cargar la persona asociada al usuario
        Long idpersona  = usuario.getPersona().getIdpersona();

        // Si hay imagen, se asocia al mapa con el Id del usuario
        if (imagen != null){
            imagenDeUsuario.put(idUsuario, imagen);
        }

        // Convertir el objeto Usuario a usuarioDto, incluyendo la imagen
        return usuarioMapper.mappearUsuarioPersonaDto(usuario, imagenDeUsuario);
    }


    // Método para obtener todos los usuarios con imagens
    @Override
    public List<UsuarioDto> obtenerTodosUsuariosConImagen() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        Map<Long, Imagen> imagenesPorUsuario = new HashMap<>();

        for(Usuario usuario: usuarios){
            Imagen imagen = cargarImagen(TipoEntidad.USUARIO, usuario.getIdUsuario());
            if (imagen != null){
                imagenesPorUsuario.put(usuario.getIdUsuario(),imagen);
            }
        }

        return usuarios.stream()
                .map(u-> usuarioMapper.mappearUsuarioDto(u, imagenesPorUsuario)).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioPersonaDto> obtenerTodosUsuariosPersonasConImagen() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        Map<Long, Imagen> imagenesPorUsuario = new HashMap<>();

        for(Usuario usuario: usuarios){
            Imagen imagen = cargarImagen(TipoEntidad.USUARIO, usuario.getIdUsuario());
            if (imagen != null){
                imagenesPorUsuario.put(usuario.getIdUsuario(),imagen);
            }
        }
        return usuarios.stream()
                .map(u-> usuarioMapper.mappearUsuarioPersonaDto(u, imagenesPorUsuario)).collect(Collectors.toList());
    }


    // Método para crear un nuevo usuario con su respectiva imagen
    @Override
    @Transactional
    public UsuarioDto crearUsuarioConImagen(UsuarioCrearDto usuarioCrearDto) throws IOException {
        System.out.println("ID rol:" + usuarioCrearDto.getIdRol() + " " + usuarioCrearDto.getNombreRol());
        System.out.println("ID estado:" + usuarioCrearDto.getIdEstado() + " " + usuarioCrearDto.getNombreEstado());

        // Busca el rol por ID o lanzar excepción si no existe
        Rol rol = rolRepository.findById(usuarioCrearDto.getIdRol())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));

        // Busca el estado por ID o lanzar excepción si no existe
        EstadoUsuario estadoUsuario =  estadoUsuarioRepository.findById(usuarioCrearDto.getIdEstado())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "estado no encontrado"));

        // Construir entidad Usuario desde el DTO y guardar en base de datos
        Usuario usuario = construirUsuarioDesdeDto(usuarioCrearDto ,rol ,estadoUsuario);
        usuarioRepository.save(usuario);

            // Obtener la imagen enviada desde DTO
            ImagenCrearDto img = usuarioCrearDto.getImagenUsuario();
            logger.info("Llego imagen?:" + img);


                // Validar si hay archivo de imagen válido
                if (img !=null && img.getArchivo() != null && !img.getArchivo().isEmpty()) {
                    logger.info("Imagen Recibida");

                    // Generar nombre Unico para el archivo
                    String nombreArchivo = UUID.randomUUID() + "_" + img.getArchivo().getOriginalFilename();
                   if (img.getTipo() != TipoEntidad.USUARIO){
                       throw  new IllegalArgumentException("Solo se permite imagen tipo Usuario");
                   }

                    Long idEntidad= usuario.getIdUsuario();
                    // Subir archivo a Amazon S3
                    //amazonS3Service.subirArchivo(img.getArchivo(), nombreArchivo, img.getTipo(), idEntidad);

                }
                else {
                    System.out.println("Imagen No Recibida");
                }
        // Retornar DTO con información completa del usuario, incluyendo imagen
        return obtenerUsuarioConImagen(usuario.getIdUsuario());
    }


    //Método para crear un nuevo usuario con su respectiva imagen
    @Override
    @Transactional
    public UsuarioPersonaDto crearUsuarioPersonaConImagen(UsuarioPersonaCrearDto usuarioPersonaCrearDto) throws IOException {
        System.out.println("DTO Completo" + usuarioPersonaCrearDto.toString());

        // Busca el rol por ID o lanzar excepción si no existe
        Rol rol = rolRepository.findById(usuarioPersonaCrearDto.getIdRol())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));


        // Busca el estado por ID o lanzar excepción si no existe
        EstadoUsuario estadoUsuario =  estadoUsuarioRepository.findById(usuarioPersonaCrearDto.getIdEstado())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "estado no encontrado"));

        // Construir entidad Usuario desde el DTO y guardar en base de datos
        Usuario usuario = construirUsuarioPersonaDesdeDto(usuarioPersonaCrearDto ,rol , estadoUsuario);
        usuarioRepository.save(usuario);

            // Obtener la imagen enviada desde DTO
            ImagenCrearDto img = usuarioPersonaCrearDto.getImagenUsuario();
            logger.info("Llego imagen?:" + img);


                // Validar si hay archivo de imagen válido
                if (img !=null && img.getArchivo() != null && !img.getArchivo().isEmpty()) {
                    logger.info("Imagen Recibida");

                    // Generar nombre Unico para el archivo
                    String nombreArchivo = UUID.randomUUID() + "_" + img.getArchivo().getOriginalFilename();
                   if (img.getTipo() != TipoEntidad.USUARIO){
                       throw  new IllegalArgumentException("Solo se permite imagen tipo Usuario");
                   }

                    Long idEntidad= usuario.getIdUsuario();
                    // Subir archivo a Amazon S3
//amazonS3Service.subirArchivo(img.getArchivo(), nombreArchivo, img.getTipo(), idEntidad);

                }
                else {
                    System.out.println("Imagen No Recibida");
                }
        // Retornar DTO con información completa del usuario, incluyendo imagen
        return obtenerUsuarioPersonaConImagen(usuario.getIdUsuario());
    }

    // Método auxiliar para construir un objeto usuario desde un DTO
    private Usuario construirUsuarioDesdeDto(UsuarioCrearDto usuarioDto, Rol rol , EstadoUsuario estadoUsuario) {

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDto.getNombreUsuario());
        usuario.setCorreo(usuarioDto.getCorreo());
        usuario.setContrasenia(passwordEncoder.encode(usuarioDto.getContrasenia()));
        usuario.setFechaRegistro(usuarioDto.getFechaRegistro());
        usuario.setRol(rol);
        usuario.setEstadoUsuario(estadoUsuario);

        return usuario;
    }


    // Método auxiliar para construir un objeto usuario persona desde un DTO
    private Usuario construirUsuarioPersonaDesdeDto( UsuarioPersonaCrearDto usuarioPersonaDto , Rol rol , EstadoUsuario estadoUsuario){

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioPersonaDto.getNombreUsuario());
        usuario.setCorreo(usuarioPersonaDto.getCorreo());
        usuario.setContrasenia(passwordEncoder.encode(usuarioPersonaDto.getContrasenia()));
        usuario.setFechaRegistro(usuarioPersonaDto.getFechaRegistro());

        usuario.setRol(rol);


        // aquí el problema:
        EstadoUsuario estado = estadoUsuarioRepository.findById(usuarioPersonaDto.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        usuario.setEstadoUsuario(estadoUsuario);


        Persona persona = new Persona();
        persona.setNombresPersona(usuarioPersonaDto.getPersonaDto().getNombresPersona());
        persona.setApellidosPersona(usuarioPersonaDto.getPersonaDto().getApellidosPersona());
        persona.setTipoDocumento(usuarioPersonaDto.getPersonaDto().getTipoDocumento());
        persona.setNumeroDocumento(usuarioPersonaDto.getPersonaDto().getNumeroDocumento());
        persona.setFechaNacimiento(usuarioPersonaDto.getPersonaDto().getFechaNacimiento());
        persona.setSexo(usuarioPersonaDto.getPersonaDto().getSexo());
        persona.setTelefono(usuarioPersonaDto.getPersonaDto().getTelefono());
        persona.setCorreoSecundario(usuarioPersonaDto.getPersonaDto().getCorreoSecundario());
        persona.setDireccion(usuarioPersonaDto.getPersonaDto().getDireccion());
        persona.setCiudad(usuarioPersonaDto.getPersonaDto().getCiudad());
        persona.setPais(usuarioPersonaDto.getPersonaDto().getPais());
        personaRepository.save(persona);

        usuario.setPersona(persona);

        return usuario;
    }

    // Método auxiliar para cargar  la imagen asociadad a un tipo entidad
    private Imagen cargarImagen(TipoEntidad tipoEntidad, Long idEntidad){
        return imagenRepository.findFirstByTipoAndIdEntidad(tipoEntidad, idEntidad);
    }



    //Carga imagenes de un tipo de entidades
    private Map<Long,List<Imagen>>  cargarImagenesPorEntidad(TipoEntidad tipoEntidad, List<Long> ids) {
        return imagenRepository.findByTipoAndIdEntidadIn(tipoEntidad, ids).stream()
                .collect(Collectors.groupingBy(Imagen::getIdEntidad));

    }
}


