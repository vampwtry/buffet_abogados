package com.project.abogados.services;

import com.project.abogados.dtos.AbogadosDTO;
import com.project.abogados.model.*;
import com.project.abogados.repository.AbogadoRepository;
import com.project.abogados.repository.TiposAbogadosRepository;
import com.project.abogados.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbogadosService {
    @Autowired
    private AbogadoRepository abogadoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private  TiposAbogadosRepository tiposAbogadosRepository;

    public List<AbogadosDTO> listAbogados(){
        List<Abogados> abogados = abogadoRepository.findAll();
        return abogados.stream().map(this::transformarDTO).collect(Collectors.toList());
    }

    private AbogadosDTO transformarDTO(Abogados abogados){
        AbogadosDTO abogadosDTO = new AbogadosDTO();
        abogadosDTO.setId_abogado(abogados.getId_abogado());
        abogadosDTO.setTarjetaProfesional(abogados.getTarjetaProfesional());
        abogadosDTO.setEspecialidad(abogados.getEspecialidad());

        if (
                abogados.getTiposAbogados()!=null
        ){
            abogadosDTO.setId_tipoAbogado(abogados.getTiposAbogados().getId_tipoAbogado());
            abogadosDTO.setTipoAbogado(abogados.getTiposAbogados().getNombre());
        }else {
            abogadosDTO.setId_tipoAbogado(null);
            abogadosDTO.setTipoAbogado(null);
        }

        if (
                abogados.getUser()!=null
        ){
            abogadosDTO.setId_user(abogados.getUser().getId_user());
            abogadosDTO.setPrimerNombreUsuario(abogados.getUser().getPrimerNombre());
            abogadosDTO.setPrimerApellidoUsuario(abogados.getUser().getPrimerApellido());
            abogadosDTO.setNumDoc(abogados.getUser().getNumeroDocumento());
        }else {
            abogadosDTO.setId_user(null);
            abogadosDTO.setPrimerNombreUsuario(null);
            abogadosDTO.setPrimerApellidoUsuario(null);
            abogadosDTO.setNumDoc(null);
        }
        return abogadosDTO;
    }

    public AbogadosDTO crearAbogado(AbogadosDTO abogadosDTO){
        Abogados nuevoAbogado = new Abogados();
        nuevoAbogado.setEspecialidad(abogadosDTO.getEspecialidad());
        nuevoAbogado.setTarjetaProfesional(abogadosDTO.getTarjetaProfesional());

        TiposAbogados tiposAbogados = tiposAbogadosRepository.findById((abogadosDTO.getId_tipoAbogado()))
                .orElseThrow(() -> new RuntimeException("Tipo de abogado no encontrado"));
        nuevoAbogado.setTiposAbogados(tiposAbogados);

        Usuarios user = usuarioRepository.findById((abogadosDTO.getId_user()))
                .orElseThrow(()-> new RuntimeException("Error"));
        nuevoAbogado.setUser(user);

        abogadoRepository.save(nuevoAbogado);

        return abogadosDTO;
    }

    public AbogadosDTO obtenerAbogadoId(Long id){
        Abogados abogados = abogadoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No se encontro al abogado"));
        AbogadosDTO abogadosDTO = transformarDTO(abogados);
        return abogadosDTO;
    }
}
