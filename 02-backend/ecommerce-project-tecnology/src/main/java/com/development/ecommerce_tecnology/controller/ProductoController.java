package com.development.ecommerce_tecnology.controller;

import com.development.ecommerce_tecnology.dto.ImagenCrearDto;
import com.development.ecommerce_tecnology.dto.ProductoActualizarDto;
import com.development.ecommerce_tecnology.dto.ProductoCrearDto;
import com.development.ecommerce_tecnology.dto.ProductoDto;
import com.development.ecommerce_tecnology.entity.Producto;
import com.development.ecommerce_tecnology.enums.TipoEntidad;
import com.development.ecommerce_tecnology.service.ProductoService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;


    public ProductoController(ProductoService productoService){
        this.productoService =productoService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoDto>>obtenerTodos(){
        return ResponseEntity.ok(productoService.obtenerTodosProductosConImagenes());
    }

    @GetMapping("/productosPaginados")
    public ResponseEntity<Page<ProductoDto>>listarProductosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size)
    {
        Pageable pageable = PageRequest.of(page, size , Sort.by("nombreProducto").ascending());
        Page<ProductoDto>productos=productoService.obtenerTodosProductosConImagenesPaginados(pageable);
        return ResponseEntity.ok(productos);
    }


    @GetMapping("/detallesProducto/{idProducto}")
    public ResponseEntity<ProductoDto> obtenerProductoPorId(@PathVariable Long idProducto){
        return  ResponseEntity.ok(productoService.obtenerProductoConImagenes(idProducto));

    }

//    @GetMapping("/categoria/{idCategoria}")
//    public ResponseEntity<List<Producto>> obtenerProductosPorCategoria(@PathVariable Long idCategoria){
//        return ResponseEntity.ok(productoService.obtenerProductosPorCategoria(idCategoria));
//    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarProductos(@RequestParam("q") String query){
        List<Producto> resultados = productoService.buscarPorCodigoONombre(query);
        return ResponseEntity.ok(resultados);
    }

//    @GetMapping("/marca/{idMarca}")
//    public ResponseEntity<List<Producto>> obtenerProductosPorMarca(@PathVariable Long idMarca){
//        return ResponseEntity.ok(productoService.obtenerProductosPorMarca(idMarca));
//    }

    @PostMapping(value= "/crearProducto" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  ResponseEntity<ProductoDto> crearProducto(

            @RequestParam("nombreProducto") String nombreProducto,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio")Double precio,
            @RequestParam(value = "fechaRegistro" , required = false)LocalDateTime fechaRegistro,
            @RequestParam(value = "fechaActualizacion" , required = false)LocalDateTime fechaActualizacion,

            @RequestParam("idCategoria") Long idCategoria,
            @RequestParam("nombreCategoria") String nombreCategoria,

            @RequestParam("idMarca") Long idMarca,
            @RequestParam("nombreMarca") String nombreMarca,


            @RequestParam(value = "archivo", required = false) List<MultipartFile> archivos,
            @RequestParam(value = "s3Key", required = false) TipoEntidad s3Key,
            @RequestParam(value = "tipo", required = false) TipoEntidad tipo


            )throws IOException{

        if(fechaRegistro == null){
            fechaRegistro = LocalDateTime.now();
        }

        if(fechaActualizacion == null){
            fechaActualizacion = LocalDateTime.now();
        }


        ProductoCrearDto productoCrearDto = new ProductoCrearDto();
        productoCrearDto.setNombreProducto(nombreProducto);
        productoCrearDto.setDescripcion(descripcion);
        productoCrearDto.setPrecio(precio);
        productoCrearDto.setFechaRegistro(fechaRegistro);
        productoCrearDto.setIdCategoria(idCategoria);
        productoCrearDto.setNombreCategoria(nombreCategoria);
        productoCrearDto.setIdMarca(idMarca);
        productoCrearDto.setNombreMarca(nombreMarca);

        productoCrearDto.setImagenesProducto(new ArrayList<>());

        System.out.println("Llego imagen a controller?:" + archivos);

        if (archivos != null && !archivos.isEmpty()) {
            for (MultipartFile archivo : archivos) {
                if (archivo != null && !archivo.isEmpty()) {

                    ImagenCrearDto imagen = new ImagenCrearDto();
                    imagen.setArchivo(archivo);
                    imagen.setTipo(tipo != null ? tipo : TipoEntidad.PRODUCTO);
                    productoCrearDto.getImagenesProducto().add(imagen);
                }
            }
        }

        ProductoDto productoGuardado = productoService.crearProductoConImagenes(productoCrearDto);

        return new ResponseEntity<>(productoGuardado, HttpStatus.CREATED);
    }


    @PutMapping("actualizarProducto/{idProducto}")
    public ResponseEntity<ProductoDto> ActualizarProducto(@PathVariable Long idProducto, @Valid @RequestBody ProductoActualizarDto productoActualizarDto)throws IOException{
        ProductoDto productoActualizado = productoService.actualizaProducto(idProducto,productoActualizarDto );
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/eliminarProducto/{idProducto}")
    public ResponseEntity<Producto> eliminarProducto(@PathVariable Long idProducto){
        productoService.eliminarProducto(idProducto);
        return ResponseEntity.noContent().build();
    }

}



