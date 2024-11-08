package com.project.abogados.services;

import com.project.abogados.dtos.TiposDocumentosDTO;
import com.project.abogados.model.Estados;
import com.project.abogados.model.TiposDocumentos;
import com.project.abogados.repository.EstadosRepository;
import com.project.abogados.repository.TiposDocumentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TiposDocumentosService {
    @Autowired
    private TiposDocumentosRepository tiposDocumentosRepository;
    @Autowired
    private EstadosRepository estadosRepository;

    private TiposDocumentosDTO transformarDTO(TiposDocumentos tiposDocumentos){
        TiposDocumentosDTO documentosDTO = new TiposDocumentosDTO();
        documentosDTO.setId(tiposDocumentos.getId_tipoDocumento());
        documentosDTO.setNombre(tiposDocumentos.getNombre());
        documentosDTO.setNomenclatura(tiposDocumentos.getNomenclatura());
        if (
                tiposDocumentos.getEstados()!= null
        ){
            documentosDTO.setEstado(tiposDocumentos.getEstados().getNombreEstado());
        }else {
            documentosDTO.setEstado(null);
        }
        return documentosDTO;
    }

    public List<TiposDocumentosDTO> listTiposDocumentos(){
        List<TiposDocumentos> documentos = tiposDocumentosRepository.findAll();
        return documentos.stream().map(this::transformarDTO).collect(Collectors.toList());

    }

    public void crearDocumento(TiposDocumentosDTO documentosDTO){
        TiposDocumentos documentos = new TiposDocumentos();
        documentos.setNombre(documentosDTO.getNombre());
        documentos.setNomenclatura(documentosDTO.getNomenclatura());
        Estados estados = estadosRepository.findByNombreEstado(documentosDTO.getEstado())
                .orElseThrow(()-> new RuntimeException("Estado no encontrado"));
        documentos.setEstados(estados);
        tiposDocumentosRepository.save(documentos);
    }




}
