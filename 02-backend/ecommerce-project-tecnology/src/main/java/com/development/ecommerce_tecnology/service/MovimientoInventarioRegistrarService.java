package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dto.MovimientoInventarioDto;
import com.development.ecommerce_tecnology.dto.MovimientoInventarioRegistrarDto;

public interface MovimientoInventarioRegistrarService {


    MovimientoInventarioDto movimientoInventarioRegistrar(MovimientoInventarioRegistrarDto movimientoInventarioRegistrarDto);

    MovimientoInventarioDto obtenerMovimientoInventarioDto(Long idMovimiento);
}
