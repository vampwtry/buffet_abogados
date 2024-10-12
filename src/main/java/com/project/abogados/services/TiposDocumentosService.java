package com.project.abogados.services;

import com.project.abogados.model.TiposDocumentos;
import com.project.abogados.repository.TiposDocumentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TiposDocumentosService {
    @Autowired
    private TiposDocumentosRepository tiposDocumentosRepository;

    public List<TiposDocumentos> listTiposDocumentos(){
        return tiposDocumentosRepository.findAll();
    }
}
