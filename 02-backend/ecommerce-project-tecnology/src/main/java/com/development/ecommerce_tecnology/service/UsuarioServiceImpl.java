package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dao.*;
import com.development.ecommerce_tecnology.dto.*;
import com.development.ecommerce_tecnology.entity.*;
import com.development.ecommerce_tecnology.enums.TipoEntidad;
import com.development.ecommerce_tecnology.mapper.UsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

// Indica que esta clase es un servicio de spring y puede ser inyectado como dependencia
@Service
public class UsuarioServiceImpl implements UsuarioService {

    // Logger para registrar eventos importantes o mensajes de depuración
        private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    // Repositorios e inyecciones de dependencias necesarias operar
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final RolService rolService;
    private final EstadoUsuarioService estadoUsuarioService;
    private final ImagenService imagenService;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioFactory usuarioFactory;
    private final UsuarioUpdater usuarioUpdater;
    private final AmazonS3Service amazonS3Service;

    // Constructor para inyectar dependencias
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RolService rolService, EstadoUsuarioService estadoUsuarioService, PersonaRepository personaRepository, ImagenService imagenService, UsuarioMapper usuarioMapper, UsuarioFactory usuarioFactory, UsuarioUpdater usuarioUpdater, AmazonS3Service amazonS3Service) {
        this.usuarioRepository = usuarioRepository;
        this.rolService = rolService;
        this.estadoUsuarioService = estadoUsuarioService;
        this.personaRepository = personaRepository;
        this.imagenService = imagenService;
        this.usuarioMapper = usuarioMapper;
        this.usuarioFactory = usuarioFactory;
        this.usuarioUpdater = usuarioUpdater;
        this.amazonS3Service = amazonS3Service;
    }

    // Metodo para obtener un usuario con su imagen asociada
    @Override
    @Transactional(readOnly= true)
    public UsuarioDto obtenerUsuarioConImagen(Long idUsuario) {

        // Buscar usuario por su ID o lanzar excepcion si no existe
        Usuario usuario= usuarioRepository.findById(idUsuario)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Obtener el ID del rol de usuario
        Long idRol = usuario.getRol().getIdRol();

        // Cargar la imagen asociada al usuario
        Imagen imagen = imagenService.obtenerImagenPrincipalPorEntidad(TipoEntidad.USUARIO, idUsuario);
        Map<Long, Imagen> imagenDeUsuario = new HashMap<>();

        if (imagen != null) {
            imagenDeUsuario.put(idUsuario, imagen);
        }

        // Convertir el objeto Usuario a usuarioDto, incluyendo la imagen
        return usuarioMapper.mappearUsuarioDto(usuario, imagenDeUsuario);
    }

    @Override
    @Transactional(readOnly= true)
    public UsuarioPersonaDto obtenerUsuarioPersonaConImagen(Long idUsuario) {

        // Buscar usuario por su ID o lanzar excepcion si no existe
        Usuario usuario= usuarioRepository.findById(idUsuario)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));


        // Cargar la imagen asociada al usuario
        Imagen imagen = imagenService.obtenerImagenPrincipalPorEntidad(TipoEntidad.USUARIO, idUsuario);


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

    // Método para obtener todos los usuarios con imagenes
    @Override
    public Page<UsuarioDto> obtenerTodosUsuariosConImagenesPaginados(Pageable pageable) {

        // Obtener página de usuarios desdel el repositorio
        Page<Usuario> usuarioPage = usuarioRepository.findAll(pageable);

        // Extraer usuarios
        List<Usuario> usuarios = usuarioPage.getContent();

        // Obtener listado IDs de Usuarios
        List<Long> idsUsuario =  usuarios.stream().map(Usuario :: getIdUsuario).toList();

        // Cargar imagenes
        Map<Long, List<Imagen>> imagenesPorUsuario =
                imagenService.obtenerImagenesDeEntidades(TipoEntidad.USUARIO, idsUsuario);

        // Adaptar a una imagen por usuario
        Map<Long, Imagen> imagenUnicaPorUsuario = imagenesPorUsuario.entrySet()
                .stream()
                .filter(e -> !e.getValue().isEmpty())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e->e.getValue().get(0)
                ));

        //Mappear a DTO
        List<UsuarioDto> usuariosDto = usuarios.stream()
                .map(u-> usuarioMapper.mappearUsuarioDto(u, imagenUnicaPorUsuario))
                .toList();

        return new PageImpl<>(usuariosDto, pageable,usuarioPage.getTotalElements());
    }

    @Override
    public Page<UsuarioPersonaDto> obtenerTodosUsuariosPersonasConImagenPaginados(Pageable pageable) {

        // Obtener página de usuarios desdel el repositorio
        Page<Usuario> usuarioPersonaPage = usuarioRepository.findAll(pageable);

        // Extraer usuarios
        List<Usuario> usuariosPersonas = usuarioPersonaPage.getContent();

        // Obtener listado IDs de Usuarios
        List<Long> idsUsuario =  usuariosPersonas.stream().map(Usuario :: getIdUsuario).toList();

        // Cargar imagenes
        Map<Long, List<Imagen>> imagenesPorUsuario =
                imagenService.obtenerImagenesDeEntidades(TipoEntidad.USUARIO, idsUsuario);

        // Adaptar a una imagen por usuario
        Map<Long, Imagen> imagenUnicaPorUsuario = imagenesPorUsuario.entrySet()
                .stream()
                .filter(e -> !e.getValue().isEmpty())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e->e.getValue().get(0)
                ));

        //Mappear a DTO
        List<UsuarioPersonaDto> usuariosPersonasDto = usuariosPersonas.stream()
                .map(u-> usuarioMapper.mappearUsuarioPersonaDto(u, imagenUnicaPorUsuario))
                .toList();


        return new PageImpl<>(usuariosPersonasDto, pageable,usuarioPersonaPage.getTotalElements());
    }

    // Método para crear un nuevo usuario con su respectiva imagen
    @Override
    @Transactional
    public UsuarioDto crearUsuarioConImagen(UsuarioCrearDto usuarioCrearDto) throws IOException {

        Rol rol = rolService.obtenerRolEntidad(usuarioCrearDto.getIdRol());
        EstadoUsuario estadoUsuario = estadoUsuarioService.obtemerUsarioEstadoEntidad(usuarioCrearDto.getIdEstado());


        Usuario usuario = usuarioFactory.crearUsuario(
                usuarioCrearDto,
                rol,
                estadoUsuario
        );
        usuarioRepository.save(usuario);

        // subir imagen asociada a usuario
        imagenService.subirImagenPorEntidad(
                usuarioCrearDto.getImagenUsuario(),
                TipoEntidad.USUARIO,
                usuario.getIdUsuario()
        );

        return obtenerUsuarioConImagen(usuario.getIdUsuario());
    }

    //Método para crear un nuevo usuario con su respectiva imagen
    @Override
    @Transactional
    public UsuarioPersonaDto crearUsuarioPersonaConImagen(UsuarioPersonaCrearDto usuarioPersonaCrearDto) throws IOException {
        System.out.println("DTO Completo" + usuarioPersonaCrearDto.toString());

        Rol rol = rolService.obtenerRolEntidad(usuarioPersonaCrearDto.getIdRol());
        EstadoUsuario estadoUsuario = estadoUsuarioService.obtemerUsarioEstadoEntidad(usuarioPersonaCrearDto.getIdEstado());


        Usuario usuario = usuarioFactory.crearUsuarioConPersona(usuarioPersonaCrearDto ,rol , estadoUsuario);
        usuarioRepository.save(usuario);

        // subir imagen asociada a usuario
        imagenService.subirImagenPorEntidad(
                usuarioPersonaCrearDto.getImagenUsuario(),
                TipoEntidad.USUARIO,
                usuario.getIdUsuario()
        );

        return obtenerUsuarioPersonaConImagen(usuario.getIdUsuario());
    }

    @Override
    @Transactional
    public UsuarioDto actualizaUsuario(
            long idUsuario,
            UsuarioActualizarDto usuarioActualizarDto) throws IOException {

        // Busca usuario existente
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Busca rol y estado
        Rol rol = rolService.obtenerRolEntidad( usuarioActualizarDto.getIdRol());
        EstadoUsuario estadoUsuario = estadoUsuarioService.obtemerUsarioEstadoEntidad(usuarioActualizarDto.getIdEstado());

        usuarioUpdater.actualizarUsuario(usuario, usuarioActualizarDto,rol,estadoUsuario);
        usuarioRepository.save(usuario);


        return obtenerUsuarioConImagen(idUsuario);
    }

}


