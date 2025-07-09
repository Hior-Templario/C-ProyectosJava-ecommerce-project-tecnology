package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.entity.Marca;

import java.util.List;

public interface MarcaService {

    List<Marca>obtenerTodas();

    Marca crear (Marca marca);

    void eliminar (Long idMarca);
}
