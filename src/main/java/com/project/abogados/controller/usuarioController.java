package com.project.abogados.controller;


import com.project.abogados.dtos.TiposDocumentosDTO;
import com.project.abogados.dtos.UsuarioDTO;
import com.project.abogados.model.Estados;
import com.project.abogados.model.Roles;
import com.project.abogados.model.TiposDocumentos;
import com.project.abogados.services.EstadosService;
import com.project.abogados.services.RolService;
import com.project.abogados.services.TiposDocumentosService;
import com.project.abogados.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class usuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;
    @Autowired
    private TiposDocumentosService tiposDocumentosService;
    @Autowired
    private EstadosService estadosService;

    @GetMapping("/crear")
    public String formularioCrear(Model model){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        model.addAttribute("usuario", usuarioDTO);
            List<Roles> roles = rolService.listaRoles();
            model.addAttribute("roles",roles);
            List<TiposDocumentosDTO> tiposDocumentos = tiposDocumentosService.listTiposDocumentos();
            model.addAttribute("tiposDocumentos", tiposDocumentos);
        return "admin/layauts/usuarios/crearUsuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") UsuarioDTO usuarioDTO, BindingResult result, Model model){
        if (result.hasErrors()){
                List<TiposDocumentosDTO> tiposDocumentos = tiposDocumentosService.listTiposDocumentos();
                model.addAttribute("tiposDocumentos",tiposDocumentos);
            return "admin/layauts/usuarios/crearUsuario?error";
        }
        usuarioService.crearUsuario(usuarioDTO);
        return "redirect:/admin/usuarios?succes";
    }

    @GetMapping("/editar/{id}")
    public String formularioEdicion(@PathVariable("id") Long id,Model model){
        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioId(id);
        List<TiposDocumentosDTO> tiposDocumentos = tiposDocumentosService.listTiposDocumentos();
        List<Estados> estados = estadosService.lsitEstados();
        model.addAttribute("usuarios", usuarioDTO);
        model.addAttribute("tipDocs",tiposDocumentos);
        model.addAttribute("estados",estados);
        return "admin/layauts/usuarios/editarUsuario";
    }

    @PostMapping("/guardarEdicion")
    public String guardarUsuarioEditado(@ModelAttribute("usuarios") UsuarioDTO usuarioDTO , BindingResult result, Model model){
        if (result.hasErrors()){
            List<TiposDocumentosDTO> tiposDocumentos = tiposDocumentosService.listTiposDocumentos();
            List<Estados> estados = estadosService.lsitEstados();
            model.addAttribute("tipDocs", tiposDocumentos);
            model.addAttribute("estados",estados);
            return "admin/layauts/usuarios/editarUsuario?ErrorEdicion";
        }
        if (usuarioDTO.getId_user() == null){
            throw new IllegalArgumentException("No se pudo procesas el ID del usuario");
        }
        usuarioService.actualizarUsuario(usuarioDTO);
        return "redirect:/admin/usuarios?succesEdit";
    }

}
