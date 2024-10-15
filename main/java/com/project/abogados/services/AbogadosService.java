package com.project.abogados.services;

import com.project.abogados.model.Abogados;
import com.project.abogados.model.TiposDocumentos;
import com.project.abogados.repository.AbogadoRepository;
import com.project.abogados.repository.TiposDocumentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbogadosService {
    @Autowired
    private AbogadoRepository abogadoRepository;

    public List<Abogados> listAbogados(){
        return abogadoRepository.findAll();
    }
}
