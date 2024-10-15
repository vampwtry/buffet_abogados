package com.project.abogados.services;

import com.project.abogados.model.Estados;
import com.project.abogados.model.TiposDocumentos;
import com.project.abogados.repository.EstadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadosService {
    @Autowired
    private EstadosRepository estadosRepository;

    public List<Estados> lsitEstados(){
        return estadosRepository.findAll();
    }

}
