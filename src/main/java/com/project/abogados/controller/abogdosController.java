package com.project.abogados.controller;

import com.project.abogados.dtos.UsuarioDTO;
import com.project.abogados.model.TiposAbogados;
import com.project.abogados.model.TiposDocumentos;
import com.project.abogados.repository.TiposAbogadosRepository;
import com.project.abogados.services.TiposAbogadosService;
import com.project.abogados.services.TiposDocumentosService;
import com.project.abogados.services.UsuarioService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/abogados")
public class abogdosController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TiposAbogadosRepository tiposAbogadosRepository;
    @Autowired
    private TiposAbogadosService tiposAbogadosService;

    @GetMapping("/Disponibles")
    public String crearAbogado(Model model){
        List<UsuarioDTO> usuarioRol = usuarioService.obtenerUsers("ROLE_USER");
        model.addAttribute("usuarios", usuarioRol);
        return "admin/layauts/Abogados/CrearAbogado";

    }
    @GetMapping("/agregar/{id}")
    public String agregarAbogado(@PathVariable("id") Long IdUsuario, Model model){
        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioId(IdUsuario);
        List<TiposAbogados> tiposAbogados = tiposAbogadosService.listTiposAbogados();
        model.addAttribute("tipAbogados",tiposAbogados);
        model.addAttribute("usuario", usuarioDTO);
        model.addAttribute("idNuevoAbogado", IdUsuario);
        return "admin/layauts/Abogados/nuevoAbogado";
    }

}
