package com.project.abogados.controller;

import com.project.abogados.dtos.AbogadosDTO;
import com.project.abogados.dtos.CasosInformalesDTO;
import com.project.abogados.dtos.TiposDocumentosDTO;
import com.project.abogados.dtos.UsuarioDTO;
import com.project.abogados.model.Abogados;
import com.project.abogados.services.AbogadosService;
import com.project.abogados.services.CasosInformalesService;
import com.project.abogados.services.TiposDocumentosService;
import com.project.abogados.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AbogadosService abogadosService;
    @Autowired
    private TiposDocumentosService documentosService;
    @Autowired
    private CasosInformalesService casosInformalesService;

    @GetMapping("/admin")
    public String paginaAdminLogin(Model model){
        model.addAttribute("conteoUsers", usuarioService.countUsuarios());
        return "admin/index";
    }

    @GetMapping("/admin/usuarios")
    public String listUsers(Model model){
        List<UsuarioDTO> usuarios = usuarioService.listUsuarios();
        model.addAttribute("usuarios",usuarios);
        return  "admin/layauts/usuarios";
    }

    @GetMapping("/admin/abogados")
    public String listAbogados(Model model){
        List<AbogadosDTO> abogados = abogadosService.listAbogados();
        model.addAttribute("abogados",abogados);
        return "admin/layauts/abogados";
    }

    @GetMapping("/admin/tiposDocs")
    public String listTiposDocs(Model model){
        List<TiposDocumentosDTO> documentosDTOS = documentosService.listTiposDocumentos();
        model.addAttribute("tipDocs",documentosDTOS);
        return "admin/layauts/Parametros/tipoDocumento/tipoDocumentos";
    }

    @GetMapping("/admin/casosInformales")
    public String listCasosInformales(Model model){
        List<CasosInformalesDTO> informalesDTOS = casosInformalesService.listCasos();
        model.addAttribute("casos",informalesDTOS);
        return "admin/layauts/casosInformales";
    }
}


