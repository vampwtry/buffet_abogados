package com.project.abogados.services;

import com.project.abogados.model.TiposAbogados;
import com.project.abogados.repository.TiposAbogadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TiposAbogadosService {
    @Autowired
    private TiposAbogadosRepository tiposAbogadosRepository;

    public List<TiposAbogados> listTiposAbogados(){
        return  tiposAbogadosRepository.findAll();
    }
}
