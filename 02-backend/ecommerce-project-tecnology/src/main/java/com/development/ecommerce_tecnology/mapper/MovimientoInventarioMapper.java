package com.development.ecommerce_tecnology.mapper;

import com.development.ecommerce_tecnology.dto.MovimientoInventarioDto;
import com.development.ecommerce_tecnology.entity.MovimientoInventario;
import com.development.ecommerce_tecnology.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class MovimientoInventarioMapper {

    public MovimientoInventarioDto mapearMovimientoInventarioDto(MovimientoInventario movimientoInventario , Producto producto) {
        MovimientoInventarioDto movimientoInventarioDto = new MovimientoInventarioDto();

        movimientoInventarioDto.setIdMovimiento(movimientoInventario.getIdMovimiento());
        movimientoInventarioDto.setTipoMovimiento(movimientoInventario.getTipoMovimiento());
        movimientoInventarioDto.setCantidad(movimientoInventario.getCantidad());
        movimientoInventarioDto.setObservaciones(movimientoInventario.getObservaciones());
        movimientoInventarioDto.setFechaMovimiento(movimientoInventario.getFechaMovimiento());

        if (producto != null){
            movimientoInventarioDto.setIdProducto(producto.getIdProducto());
            movimientoInventarioDto.setNombreProducto(producto.getNombreProducto());
        }

        return movimientoInventarioDto;
    }
}
