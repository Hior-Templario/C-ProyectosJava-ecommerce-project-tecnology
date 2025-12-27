package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dto.MarcaCrearDto;
import com.development.ecommerce_tecnology.dto.MarcaDto;
import com.development.ecommerce_tecnology.entity.Marca;

import java.io.IOException;
import java.util.List;

public interface MarcaService {

    Marca obtenerMarcaPorId(Long idMarca);

    MarcaDto obtenerMarcaConImagenes(Long idMarca);

    List<MarcaDto> obtenerTodasMarcasConImagenes();

    MarcaDto crearMarcaConImagen(MarcaCrearDto marcaCrearDto)throws IOException;

    void eliminarMarca (Long idMarca);
}
