package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dao.MovimientoInventarioRepository;
import com.development.ecommerce_tecnology.dao.ProductoRepository;
import com.development.ecommerce_tecnology.dto.MovimientoInventarioDto;
import com.development.ecommerce_tecnology.dto.MovimientoInventarioRegistrarDto;
import com.development.ecommerce_tecnology.entity.MovimientoInventario;
import com.development.ecommerce_tecnology.entity.Producto;
import com.development.ecommerce_tecnology.mapper.MovimientoInventarioMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MovimientoInventarioRegistrarServiceImpl implements  MovimientoInventarioRegistrarService{

    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final ProductoRepository productoRepository;
    private final MovimientoInventarioMapper movimientoInventarioMapper;

    public MovimientoInventarioRegistrarServiceImpl(ProductoRepository productoRepository, MovimientoInventarioRepository movimientoInventarioRepository, MovimientoInventarioMapper movimientoInventarioMapper) {
        this.movimientoInventarioRepository = movimientoInventarioRepository;
        this.productoRepository = productoRepository;
        this.movimientoInventarioMapper = movimientoInventarioMapper;
    }

    @Override
    public MovimientoInventarioDto obtenerMovimientoInventarioDto(Long idMovimiento){

        MovimientoInventario movimientoInventario = movimientoInventarioRepository.findById(idMovimiento)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "producto no encontrado"));

        System.out.println("movimientoInventario encontrado" + idMovimiento);
        Producto producto = movimientoInventario.getProducto();

        return movimientoInventarioMapper.mapearMovimientoInventarioDto(movimientoInventario, producto);

    }

    @Override
    public MovimientoInventarioDto movimientoInventarioRegistrar(MovimientoInventarioRegistrarDto movimientoInventarioRegistrarDto) {

        // Busca el producto
        Producto producto = productoRepository.findById(movimientoInventarioRegistrarDto.getIdProducto())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "producto no encontrado"));

        // Crea el movimiento
        MovimientoInventario movimientoInventario = construirMovimientoInventario(movimientoInventarioRegistrarDto);
        movimientoInventario.setProducto(producto);

        // Actuaizar el stock segun el movimiento
        if(movimientoInventarioRegistrarDto.getTipoMovimiento().equalsIgnoreCase("ENTRADA")){
            producto.setStock(producto.getStock() + movimientoInventarioRegistrarDto.getCantidad());

        } else if (movimientoInventarioRegistrarDto.getTipoMovimiento().equalsIgnoreCase("SALIDA")){
            if (producto.getStock() < movimientoInventarioRegistrarDto.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para realizar salida");
            }
            producto.setStock(producto.getStock() - movimientoInventarioRegistrarDto.getCantidad());
        }

        MovimientoInventario guardado = movimientoInventarioRepository.save(movimientoInventario);
        productoRepository.save(producto);

        return movimientoInventarioMapper.mapearMovimientoInventarioDto(guardado, producto);

    }

    private MovimientoInventario construirMovimientoInventario (MovimientoInventarioRegistrarDto movimientoInventarioRegistrarDto){

        MovimientoInventario movimientoInventario = new MovimientoInventario();

        movimientoInventario.setTipoMovimiento(movimientoInventarioRegistrarDto.getTipoMovimiento());
        movimientoInventario.setCantidad(movimientoInventarioRegistrarDto.getCantidad());
        movimientoInventario.setObservaciones(movimientoInventarioRegistrarDto.getObservaciones());

        return movimientoInventario;


    }
}

