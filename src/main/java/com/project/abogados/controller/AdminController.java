package com.project.abogados.controller;

import com.project.abogados.dtos.UsuarioDTO;
import com.project.abogados.repository.UsuarioRepository;
import com.project.abogados.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/admin")
    public String paginaAdminLogin() {
        return "admin/index";
    }

    @GetMapping("/admin/usuarios")
    public String listUsers(Model model){
        List<UsuarioDTO> usuarios = usuarioService.listUsuarios();
        model.addAttribute("usuarios",usuarios);
        return  "admin/layauts/usuarios";
    }
}


