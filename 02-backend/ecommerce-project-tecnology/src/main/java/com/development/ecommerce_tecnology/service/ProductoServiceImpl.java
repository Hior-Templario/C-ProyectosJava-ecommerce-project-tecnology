package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dao.ProductoRepository;
import com.development.ecommerce_tecnology.entity.Producto;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    ProductoServiceImpl(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    @Override
    public Producto obtenerProductoPorId(Long idProducto) {
        return productoRepository.findById(idProducto)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    @Override
    @Transactional(readOnly= true)
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @Override
    @Transactional
    public List<Producto> obtenerPorCategoria(Long idCategoria) {
        return productoRepository.findByCategoria_IdCategoria(idCategoria);
    }

    @Override
    public List<Producto> obtenerPorMarca(Long idMarca) {
        return productoRepository.findByMarca_IdMarca(idMarca);
    }

    @Override
    @Transactional
    public Producto crear(Producto producto) {
        if (producto.getPrecio() < 0 ){
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        return productoRepository.save(producto);
    }

    @Override
    public void eliminar(Long idProducto) {
         productoRepository.deleteById(idProducto);

    }


}
