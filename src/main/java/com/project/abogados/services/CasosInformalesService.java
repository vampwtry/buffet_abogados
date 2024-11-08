package com.project.abogados.services;

import com.project.abogados.dtos.CasosInformalesDTO;
import com.project.abogados.model.CasosInformales;
import com.project.abogados.model.TiposAbogados;
import com.project.abogados.repository.CasosInformalesRepository;
import com.project.abogados.repository.TiposAbogadosRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CasosInformalesService {
    @Autowired
    private TiposAbogadosRepository tiposAbogadosRepository;
    @Autowired
    private CasosInformalesRepository casosInformalesRepository;

    private CasosInformalesDTO transformDTO(CasosInformales casosInformales){
        CasosInformalesDTO casosDTO = new CasosInformalesDTO();

        casosDTO.setId_casoInformal(casosInformales.getId_casoInformal());
        casosDTO.setCorreo(casosInformales.getCorreo());
        casosDTO.setTelefono(casosInformales.getTelefono());
        casosDTO.setDescripcion(casosInformales.getDescripcion());
        casosDTO.setNombresCompletos(casosInformales.getNombresCompletos());

        if (
                casosInformales.getTiposAbogados()!=null
        ){
            casosDTO.setId_tipoAbogado(casosInformales.getTiposAbogados().getId_tipoAbogado());
            casosDTO.setTipoAbogado(casosInformales.getTiposAbogados().getNombre());
        }else {
            casosDTO.setId_tipoAbogado(null);
            casosDTO.setTipoAbogado(null);
        }

        return casosDTO;
    }


    public void crearAbogado(CasosInformalesDTO casosDTO){
        CasosInformales casosInformales = new CasosInformales();

        casosInformales.setNombresCompletos(casosDTO.getNombresCompletos());
        casosInformales.setCorreo(casosDTO.getCorreo());
        casosInformales.setTelefono(casosDTO.getTelefono());
        casosInformales.setDescripcion(casosDTO.getDescripcion());

        TiposAbogados tiposAbogados = tiposAbogadosRepository.findById(casosDTO.getId_tipoAbogado())
                .orElseThrow(()-> new RuntimeException("No se encontro el tipo de abogado"));
        casosInformales.setTiposAbogados(tiposAbogados);

        casosInformalesRepository.save(casosInformales);
    }

}
