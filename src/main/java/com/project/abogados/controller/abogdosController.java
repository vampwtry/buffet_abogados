package com.project.abogados.controller;

import com.project.abogados.dtos.AbogadosDTO;
import com.project.abogados.dtos.UsuarioDTO;
import com.project.abogados.model.Abogados;
import com.project.abogados.model.Roles;
import com.project.abogados.model.TiposAbogados;
import com.project.abogados.model.TiposDocumentos;
import com.project.abogados.repository.AbogadoRepository;
import com.project.abogados.repository.RolRepository;
import com.project.abogados.repository.TiposAbogadosRepository;
import com.project.abogados.services.*;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/abogados")
public class abogdosController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TiposAbogadosRepository tiposAbogadosRepository;
    @Autowired
    private TiposAbogadosService tiposAbogadosService;
    @Autowired
    private AbogadoRepository abogadoRepository;
    @Autowired
    private AbogadosService abogadosService;
    @Autowired
    private RolRepository rolRepository;


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
        AbogadosDTO abogadoDTO = new AbogadosDTO();
        abogadoDTO.setId_user(usuarioDTO.getId_user());
        model.addAttribute("abogado", abogadoDTO);
        model.addAttribute("tipAbogados", tiposAbogados);
        model.addAttribute("usuario", usuarioDTO);

        return "admin/layauts/Abogados/nuevoAbogado";
    }


    @PostMapping("/guardar")
    public String guardarAbogado(@ModelAttribute("abogado") AbogadosDTO abogadosDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<TiposAbogados> tiposAbogados = tiposAbogadosService.listTiposAbogados();
            model.addAttribute("tipAbogados", tiposAbogados);
            return "admin/layauts/usuarios/crearUsuario?error";
        }

        try {
            abogadosService.crearAbogado(abogadosDTO);
            UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioId(abogadosDTO.getId_user());
            Optional<Roles> rolAbogado = rolRepository.findByNombreRol("ROLE_ABOGADO");

            if (rolAbogado.isPresent()) {
                usuarioDTO.setRolID(rolAbogado.get().getIdRol());
                usuarioService.actualizarRolUsuario(usuarioDTO);
            } else {
                model.addAttribute("errorMessage", "No se encontró el rol de abogado.");
                List<TiposAbogados> tiposAbogados = tiposAbogadosService.listTiposAbogados();
                model.addAttribute("tipAbogados", tiposAbogados);
                return "admin/layauts/Abogados/nuevoAbogado";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al guardar el abogado: " + e.getMessage());
            List<TiposAbogados> tiposAbogados = tiposAbogadosService.listTiposAbogados();
            model.addAttribute("tipAbogados", tiposAbogados);
            return "admin/layauts/Abogados/nuevoAbogado";
        }

        return "redirect:/admin/abogados?success";
    }

    @GetMapping("/detalle/{id}")
    public String detalleAbogado(@PathVariable("id") Long id, Model model) {
        AbogadosDTO abogadosDTO = abogadosService.obtenerAbogadoId(id);
        model.addAttribute("abogado", abogadosDTO);
        return "redirect:admin/layauts/abogados";
    }



}
