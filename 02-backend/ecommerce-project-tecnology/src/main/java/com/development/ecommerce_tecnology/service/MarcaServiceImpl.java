package com.development.ecommerce_tecnology.service;

import com.development.ecommerce_tecnology.dao.MarcaRepository;
import com.development.ecommerce_tecnology.entity.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MarcaServiceImpl implements MarcaService{


    private final MarcaRepository marcaRepository;

    public MarcaServiceImpl(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Marca> obtenerTodas() {

        return marcaRepository.findAll();
    }

    @Override
    public Marca crear(Marca marca) {

        return marcaRepository.save( marca);
    }

    @Override
    public void eliminar(Long idMarca) {

        marcaRepository.deleteById(idMarca);

    }
}
