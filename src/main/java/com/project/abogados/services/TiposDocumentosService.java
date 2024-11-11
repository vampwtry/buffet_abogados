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
        Estados estados = estadosRepository.findByNombreEstado("ACTIVO")
                .orElseThrow(()-> new RuntimeException("Estado no encontrado"));
        documentos.setEstados(estados);
        tiposDocumentosRepository.save(documentos);
    }

    public TiposDocumentosDTO obtenerTipDocID(Long id){
        TiposDocumentos documentos = tiposDocumentosRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No se encontro el ID de ese registro"));
        TiposDocumentosDTO documentosDTO = transformarDTO(documentos);
        return documentosDTO;
    }

    public void actualizarTipDocs(TiposDocumentosDTO documentosDTO){
        TiposDocumentos documentos = tiposDocumentosRepository.findById(documentosDTO.getId())
                .orElseThrow(()-> new RuntimeException("El tipDoc no fue encontrado"));
        documentos.setNombre(documentosDTO.getNombre());
        documentos.setNomenclatura(documentos.getNomenclatura());
        Estados estados = estadosRepository.findById(documentosDTO.getId_Estado())
                .orElseThrow(()-> new RuntimeException("No se encontro el estado"));
        documentos.setEstados(estados);
        tiposDocumentosRepository.save(documentos);
    }




}
