package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dao.CategoriaRepository;
import com.development.ecommerce_tecnology.dao.ImagenRepository;
import com.development.ecommerce_tecnology.dao.MarcaRepository;
import com.development.ecommerce_tecnology.dao.ProductoRepository;
import com.development.ecommerce_tecnology.dto.*;
import com.development.ecommerce_tecnology.entity.*;

import com.development.ecommerce_tecnology.enums.TipoEntidad;
import com.development.ecommerce_tecnology.mapper.ProductoMapper;
import jakarta.persistence.EntityNotFoundException;
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
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ImagenRepository imagenRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final AmazonS3Service amazonS3Service;
    private final ProductoMapper productoMapper;

    private static final Logger logger = LoggerFactory.getLogger(ProductoServiceImpl.class);

    public ProductoServiceImpl(ProductoRepository productoRepository, ImagenRepository imagenRepository, CategoriaRepository categoriaRepository, MarcaRepository marcaRepository, AmazonS3Service amazonS3Service, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.imagenRepository = imagenRepository;
        this.categoriaRepository = categoriaRepository;
        this.marcaRepository = marcaRepository;
        this.amazonS3Service = amazonS3Service;
        this.productoMapper = productoMapper;
    }


    @Override
    @Transactional(readOnly= true)
    public List<ProductoDto> obtenerTodosProductosConImagenes() {

        List<Producto> productos =  productoRepository.findAll();

        // Obtener listado IDs de Marcas Categorias y Productos
        List<Long> idsProducto  =  productos.stream().map(Producto :: getIdProducto)
                .collect(Collectors.toList());
        List<Long> idsCategoria = productos.stream().map(p -> p.getCategoria().getIdCategoria())
                .distinct()
                .collect(Collectors.toList());
        List<Long>idsMarca= productos.stream().map(p -> p.getMarca().getIdMarca())
                .distinct()
                .collect(Collectors.toList());

        //Cargar imagenes en bloque de cada listado
        Map<Long, List<Imagen>> imagenesPorProducto = cargarImagenesPorEntidad(TipoEntidad.PRODUCTO, idsProducto);
        Map<Long, List<Imagen>> imagenesPorCategoria = cargarImagenesPorEntidad(TipoEntidad.CATEGORIA, idsCategoria);
        Map<Long, List<Imagen>> imagenesPorMarca =  cargarImagenesPorEntidad(TipoEntidad.MARCA, idsMarca);

        return productos.stream()
                .map( p -> productoMapper.mappearProductoDto(p, imagenesPorProducto,imagenesPorCategoria,imagenesPorMarca))
                .toList();
    }


    @Override
    @Transactional(readOnly= true)
    public Page<ProductoDto> obtenerTodosProductosConImagenesPaginados(Pageable pageable) {

        // Obtener la página de productos desde el el repocitorio
        Page<Producto> productosPage = productoRepository.findAll(pageable);

        // Extraer productos
        List<Producto> productos =  productosPage.getContent();

        // Obtener listado IDs de Marcas Categorias y Productos
        List<Long> idsProducto  =  productos.stream().map(Producto :: getIdProducto)
                .collect(Collectors.toList());
        List<Long> idsCategoria = productos.stream().map(p -> p.getCategoria().getIdCategoria())
                .distinct()
                .collect(Collectors.toList());
        List<Long>idsMarca= productos.stream().map(p -> p.getMarca().getIdMarca())
                .distinct()
                .collect(Collectors.toList());

        //Cargar imagenes en bloque de cada listado
        Map<Long, List<Imagen>> imagenesPorProducto = cargarImagenesPorEntidad(TipoEntidad.PRODUCTO, idsProducto);
        Map<Long, List<Imagen>> imagenesPorCategoria = cargarImagenesPorEntidad(TipoEntidad.CATEGORIA, idsCategoria);
        Map<Long, List<Imagen>> imagenesPorMarca =  cargarImagenesPorEntidad(TipoEntidad.MARCA, idsMarca);

        // Convertir los productos a DTO

        List<ProductoDto> productosDto= productos.stream()
                .map( p -> productoMapper.mappearProductoDto(p, imagenesPorProducto,imagenesPorCategoria,imagenesPorMarca))
                .toList();

        // Retonar una nueva página con la misma informacion de paginación
        return new PageImpl<>(productosDto, pageable ,productosPage.getTotalElements());
    }


    @Override
    @Transactional(readOnly = true)
    public ProductoDto obtenerProductoConImagenes(Long idProducto) {
        Producto producto= productoRepository.findById(idProducto)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        Long idCategoria = producto.getCategoria().getIdCategoria();
        Long idMarca = producto.getMarca().getIdMarca();

        // Crear mapas con  una sola entrada de una
        Map<Long, List<Imagen>> imagenesPorProducto = Map.of(idProducto, cargarImagenes(TipoEntidad.PRODUCTO, idProducto));
        Map<Long, List<Imagen>> imagenesPorCategoria = Map.of(idCategoria, cargarImagenes(TipoEntidad.CATEGORIA, idCategoria));
        Map<Long, List<Imagen>> imagenesPorMarca = Map.of(idMarca, cargarImagenes(TipoEntidad.MARCA, idMarca));


        return productoMapper.mappearProductoDto(producto, imagenesPorProducto,imagenesPorCategoria, imagenesPorMarca );
    }

    @Override
    public List<Producto> buscarPorCodigoONombre(String query) {

        if(query == null || query.trim().isEmpty()){
            return List.of(); // Retorna lista vacia
        }

        // Busca coincidencia tanto por codigo como por nombre

        return productoRepository.findByCodigoProductoContainingIgnoreCaseOrNombreProductoContainingIgnoreCase(query, query);

    }

    @Override
    @Transactional
    public ProductoDto crearProductoConImagenes(ProductoCrearDto productocrearDto) throws IOException{

        System.out.println("ID Categoria desde DTO:" + productocrearDto.getIdCategoria());
        System.out.println("ID marca desde DTO:" + productocrearDto.getIdMarca());

        Categoria categoria = categoriaRepository.findById(productocrearDto.getIdCategoria())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "categoria no encontrado"));

        Marca marca = marcaRepository.findById(productocrearDto.getIdMarca())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca no encontrada"));


        String prefijo = categoriaRepository.obtenerPrefijoPorId(productocrearDto.getIdCategoria());

        if (prefijo == null){
            prefijo = "GEN";
        }

        // Numero correlativo
        Long cantidad  = productoRepository.contarPorCategoria(productocrearDto.getIdCategoria()) +1;


        // Formato del codigo
        String anioMes = YearMonth.now().toString().replace(".","");
        String codigoGenerado = String.format("PROD-%s-%s-%04d", prefijo, anioMes, cantidad);
        System.out.println(codigoGenerado);


        // Construir Producto
        Producto producto = construirProductoDesdeDto(productocrearDto, categoria , marca , codigoGenerado);
        productoRepository.save(producto);


        // Obtener lalista de imagenes enviadas desde DTO
        List<ImagenCrearDto> img = productocrearDto.getImagenesProducto();
        logger.info("Llegaron imagenes?:" + img);

        if (img!= null && !img.isEmpty()  ){
            logger.info("Imagenes recibidas");

            for(ImagenCrearDto imagen : img){
                Long idEntidad= producto.getIdProducto();
                String nombreArchivo = UUID.randomUUID() + "_" + imagen.getArchivo().getOriginalFilename();

                amazonS3Service.subirArchivo(imagen.getArchivo(), nombreArchivo,imagen.getTipo(), idEntidad);

            }

        }
         else{
             System.out.println("Imagen no recibida");
                 }

        return obtenerProductoConImagenes(producto.getIdProducto());

    }

    @Override
    @Transactional
    public List<Producto> obtenerProductosPorCategoria(Long idCategoria) {

        if (!categoriaRepository.existsById(idCategoria)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Categoria no encontrada");
        }

        List<Producto> productos = productoRepository.findByCategoria_IdCategoria(idCategoria);
        productos.forEach(this::cargarImagenesParaProducto);

        return productos;
    }

    @Override
    @Transactional
    public List<Producto> obtenerProductosPorMarca(Long idMarca) {

        if (!marcaRepository.existsById(idMarca)){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Marca No encontrada");
        }

        List<Producto> productos = productoRepository.findByMarca_IdMarca(idMarca);
        productos.forEach(this::cargarImagenesParaProducto);

        return productos;
    }


    @Override
    public void eliminarProducto(Long idProducto) {
        if(!productoRepository.existsById(idProducto)){
            throw new EntityNotFoundException("Producto no encontrado para eliminar");
        }
        productoRepository.deleteById(idProducto);

    }



    //=================================== Metodos auxiliares==============================

    //Carga imagenes de un tipo de entidad
    private List<Imagen> cargarImagenes(TipoEntidad tipoEntidad , Long idEntidad){
        return imagenRepository.findByTipoAndIdEntidad(tipoEntidad, idEntidad);

    }

    //Carga imagenes de un tipo de entidades
    private Map<Long,List<Imagen>>  cargarImagenesPorEntidad(TipoEntidad tipoEntidad, List<Long> ids) {
        return imagenRepository.findByTipoAndIdEntidadIn(tipoEntidad, ids).stream()
                .collect(Collectors.groupingBy(Imagen::getIdEntidad));

    }


    //Construir  Producto desde Dto
    private Producto construirProductoDesdeDto(ProductoCrearDto productoDto, Categoria categoria, Marca marca, String codigoGenerado) {


        Producto producto = new Producto();
        producto.setCodigoProducto(codigoGenerado);
        producto.setNombreProducto(productoDto.getNombreProducto());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setPrecio(productoDto.getPrecio());
        producto.setStock(0);
        producto.setFechaRegistro(productoDto.getFechaRegistro());
        producto.setFechaActualizacion(productoDto.getFechaActualizacion());
        producto.setCategoria(categoria);
        producto.setMarca(marca);

        return producto;
    }

    private void cargarImagenesParaProducto(Producto producto){

        producto.setImagenes(cargarImagenes(TipoEntidad.PRODUCTO, producto.getIdProducto()));

        if (producto.getCategoria() != null){
            producto.getCategoria().setImagenes(cargarImagenes(TipoEntidad.CATEGORIA, producto.getCategoria().getIdCategoria()));
        }

        if(producto.getMarca() != null){
            producto.getMarca().setImagenes(cargarImagenes(TipoEntidad.MARCA, producto.getMarca().getIdMarca()));
        }

    }

}
