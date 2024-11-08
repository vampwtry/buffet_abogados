package com.project.abogados.controller;

import com.project.abogados.dtos.CasosInformalesDTO;
import com.project.abogados.dtos.UsuarioDTO;
import com.project.abogados.model.TiposAbogados;
import com.project.abogados.repository.TiposAbogadosRepository;
import com.project.abogados.services.CasosInformalesService;
import com.project.abogados.services.TiposAbogadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class inicioController {
    @Autowired
    private TiposAbogadosService tiposAbogadosService;
    @Autowired
    private CasosInformalesService casosInformalesService;


    @GetMapping("/")
    public String inicio(Model model){
        List<TiposAbogados>tiposAbogados = tiposAbogadosService.listTiposAbogados();
        model.addAttribute("tiposAbogados",tiposAbogados);
        return "index";
    }

    @PostMapping("/guardarCasoInformal")
    public String guardarCasoInformal(@ModelAttribute("casoInformal")CasosInformalesDTO casosDTO, BindingResult result, Model model){
        if (result.hasErrors()){
            List<TiposAbogados> tiposAbogados = tiposAbogadosService.listTiposAbogados();
            model.addAttribute("tiposAbogados",tiposAbogados);
            return "index";
        }
        casosInformalesService.crearAbogado(casosDTO);
        return "redirect:index?succes";
    }


}
