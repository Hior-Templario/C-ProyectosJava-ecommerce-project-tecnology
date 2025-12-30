package com.development.ecommerce_tecnology.service;

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
import java.util.stream.Collectors;


@Service
public class ProductoServiceImpl implements ProductoService {

    // Repositorios y servicios necesarios para operaciones sobre productos, categorías, marcas e imágenes
    private final ProductoRepository productoRepository;
    private final CategoriaService categoriaService;
    private final MarcaService marcaService;
    private final ImagenService imagenService;
    private final ProductoMapper productoMapper;
    private final ProductoFactory productoFactory;
    private final ProductoUpdater productoUpdater;

    // Logger para registrar eventos y errores
    private static final Logger logger =
            LoggerFactory.getLogger(ProductoServiceImpl.class);

    // Constructor para inyección de dependencias
    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaService categoriaService, MarcaService marcaService, ImagenService imagenService, ProductoMapper productoMapper, ProductoFactory productoFactory, ProductoUpdater productoUpdater) {
        this.productoRepository = productoRepository;
        this.categoriaService = categoriaService;
        this.marcaService = marcaService;
        this.imagenService = imagenService;
        this.productoMapper = productoMapper;
        this.productoFactory = productoFactory;
        this.productoUpdater = productoUpdater;
    }

    @Override
    @Transactional(readOnly= true)
    public List<ProductoDto> obtenerTodosProductosConImagenes() {

        List<Producto> productos =  productoRepository.findAll();

        // Obtener listado de IDs de productos, categorías y marcas
        List<Long> idsProducto  =  productos.stream().map(Producto :: getIdProducto).collect(Collectors.toList());
        List<Long> idsCategoria = productos.stream().map(p -> p.getCategoria().getIdCategoria()).distinct().collect(Collectors.toList());
        List<Long>idsMarca= productos.stream().map(p -> p.getMarca().getIdMarca()).distinct().collect(Collectors.toList());

        //Cargar imagenes en bloque pra cada entidad
        Map<Long, List<Imagen>> imagenesProducto = imagenService.obtenerImagenesDeEntidades(TipoEntidad.PRODUCTO, idsProducto);
        Map<Long, List<Imagen>> imagenesCategoria = imagenService.obtenerImagenesDeEntidades(TipoEntidad.CATEGORIA, idsCategoria);
        Map<Long, List<Imagen>> imagenesMarca =  imagenService.obtenerImagenesDeEntidades(TipoEntidad.MARCA, idsMarca);

        // Mapear productos a DTO incluyendo las imágenes
        return productos.stream()
                .map( p -> productoMapper.mappearProductoDto(p, imagenesProducto,imagenesCategoria,imagenesMarca))
                .toList();
    }

    @Override
    public Page<ProductoDto> obtenerTodosProductosConImagenesPaginados(Pageable pageable) {

        // Obtener página de usuarios desdel el repositorio
        Page<Producto> productosPage = productoRepository.findAll(pageable);

        // Extraer productos
        List<Producto> productos =  productosPage.getContent();

        // Obtener listado IDs de Marcas Categorias y Productos
        List<Long> idsProducto  =  productos.stream().map(Producto :: getIdProducto).collect(Collectors.toList());
        List<Long> idsCategoria = productos.stream().map(p -> p.getCategoria().getIdCategoria()).distinct().collect(Collectors.toList());
        List<Long>idsMarca= productos.stream().map(p -> p.getMarca().getIdMarca()).distinct().collect(Collectors.toList());

        //Cargar imagenes en bloque de cada listado
        Map<Long, List<Imagen>> imagenesPorProducto = imagenService.obtenerImagenesDeEntidades(TipoEntidad.PRODUCTO, idsProducto);
        Map<Long, List<Imagen>> imagenesPorCategoria = imagenService.obtenerImagenesDeEntidades(TipoEntidad.CATEGORIA, idsCategoria);
        Map<Long, List<Imagen>> imagenesPorMarca =  imagenService.obtenerImagenesDeEntidades(TipoEntidad.MARCA, idsMarca);

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

        // Buscar productos por ID
        Producto producto= productoRepository.findById(idProducto)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        Long idCategoria = producto.getCategoria().getIdCategoria();
        Long idMarca = producto.getMarca().getIdMarca();

        // Crear mapas con  una sola entrada para obtener imagenes
        Map<Long, List<Imagen>> imagenesPorProducto = Map.of(idProducto, imagenService.obtenerImagenesDeEntidad(TipoEntidad.PRODUCTO, idProducto));
        Map<Long, List<Imagen>> imagenesPorCategoria = Map.of(idCategoria, imagenService.obtenerImagenesDeEntidad(TipoEntidad.CATEGORIA, idCategoria));
        Map<Long, List<Imagen>> imagenesPorMarca = Map.of(idMarca, imagenService.obtenerImagenesDeEntidad(TipoEntidad.MARCA, idMarca));

        // Mapear producto a DTO
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

        Categoria categoria = categoriaService.obtenerCategoriaPorId(productocrearDto.getIdCategoria());
        Marca marca = marcaService.obtenerMarcaPorId(productocrearDto.getIdMarca());

        // Genera código de producto
        String prefijo = categoriaService.obtenerPrefijoCategoria(productocrearDto.getIdCategoria());


        // Numero correlativo
        Long cantidad  = productoRepository.contarPorCategoria(productocrearDto.getIdCategoria()) +1;

        // Formato del codigo
        String anioMes = YearMonth.now().toString().replace(".","");
        String codigoGenerado = String.format("PROD-%s-%s-%04d", prefijo, anioMes, cantidad);

        // Construir y guardar producto
        Producto producto = productoFactory.crearProducto(productocrearDto, categoria , marca , codigoGenerado);
        productoRepository.save(producto);

        // Subir imagenes asociadas al producto
        imagenService.subirImagenesPorEntidad(
                productocrearDto.getImagenesProducto(),
                TipoEntidad.PRODUCTO,
                producto.getIdProducto());

        // Retonar producto con imágenes
        return obtenerProductoConImagenes(producto.getIdProducto());

    }

    @Override
    @Transactional
    public ProductoDto actualizaProducto(Long idProducto, ProductoActualizarDto productoActualizarDto) throws IOException {

        // Buscar producto existentes
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        // Buscar categoria y marca nueva
        Categoria categoria = categoriaService.obtenerCategoriaPorId(productoActualizarDto.getIdCategoria());

        Marca marca = marcaService.obtenerMarcaPorId(productoActualizarDto.getIdMarca());


        productoUpdater.actualizarProducto(producto, productoActualizarDto,categoria, marca);
        productoRepository.save(producto);

        // Convertir a DTO y retonar
        return  obtenerProductoConImagenes(idProducto);
    }

    @Override
    @Transactional
    public void eliminarProducto(Long idProducto) {

        // Verificar existencia del producto
        if(!productoRepository.existsById(idProducto)){
            throw new EntityNotFoundException("Producto no encontrado para eliminar");
        }

        // Eliminar imagenes asociadas ates de borrar el producto
        imagenService.eliminarImagenesPorEntidad(
                TipoEntidad.PRODUCTO,idProducto
        );

        // Eliminar producto
        productoRepository.deleteById(idProducto);

    }


    // Convertir entidad Producto a Dto
    private ProductoDto convertirADto(Producto producto) {

        ProductoDto productoDto = new ProductoDto();

        productoDto.setIdProducto(producto.getIdProducto());
        productoDto.setCodigoProducto(producto.getCodigoProducto());
        productoDto.setNombreProducto(producto.getNombreProducto());
        productoDto.setDescripcion(producto.getDescripcion());
        productoDto.setPrecio(producto.getPrecio());
        productoDto.setFechaRegistro(producto.getFechaRegistro());
        productoDto.setFechaActualizacion(producto.getFechaActualizacion());


        productoDto.setIdCategoria(producto.getCategoria().getIdCategoria());
        productoDto.setNombreCategoria(producto.getCategoria().getNombreCategoria());

        productoDto.setIdMarca(producto.getMarca().getIdMarca());
        productoDto.setNombreMarca(producto.getMarca().getNombreMarca());

        return productoDto;
    }


    //    @Override
//    @Transactional
//    public List<Producto> obtenerProductosPorCategoria(Long idCategoria) {
//
//        if (!categoriaRepository.existsById(idCategoria)){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Categoria no encontrada");
//        }
//
//        List<Producto> productos = productoRepository.findByCategoria_IdCategoria(idCategoria);
//        productos.forEach(this::cargarImagenesParaProducto);
//
//        return productos;
//   }
//
//    @Override
//    @Transactional
//    public List<Producto> obtenerProductosPorMarca(Long idMarca) {
//
//        if (!marcaRepository.existsById(idMarca)){
//
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Marca No encontrada");
//        }
//
//        List<Producto> productos = productoRepository.findByMarca_IdMarca(idMarca);
//        productos.forEach(this::cargarImagenesParaProducto);
//
//        return productos;
//    }

}
