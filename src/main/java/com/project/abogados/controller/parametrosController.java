package com.project.abogados.controller;

import com.project.abogados.dtos.EstadoDTO;
import com.project.abogados.dtos.TiposDocumentosDTO;
import com.project.abogados.dtos.UsuarioDTO;
import com.project.abogados.model.Estados;
import com.project.abogados.services.EstadosService;
import com.project.abogados.services.TiposDocumentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ParamSystem")
public class parametrosController {
    @Autowired
    private TiposDocumentosService documentosService;
    @Autowired
    private EstadosService estadosService;

    @GetMapping("/NuevoTipDoc")
    public String formularioCrear(Model model){
        TiposDocumentosDTO documentosDTO = new TiposDocumentosDTO();
        model.addAttribute("TipDocs", documentosDTO);
        return "admin/layauts/Parametros/tipoDocumento/crearTipoDocumento";
    }

    @PostMapping("/guardarTipDoc")
    public String guardarTipDoc(@ModelAttribute("TipDocs") TiposDocumentosDTO documentosDTO, BindingResult result, Model model){
        if (result.hasErrors()){
            return "redirect:/NuevoTipDoc?error";
        }
        documentosService.crearDocumento(documentosDTO);
        return "redirect:/admin/tiposDocs?succes";
    }

    @GetMapping("/EditarTipDoc/{id}")
    public String formularioEdicion(@PathVariable("id") Long id, Model model){
        TiposDocumentosDTO documentosDTO = documentosService.obtenerTipDocID(id);
        List<Estados> estados = estadosService.lsitEstados();
        model.addAttribute("TipDocs",documentosDTO);
        model.addAttribute("estados",estados);
        return "admin/layauts/Parametros/tipoDocumento/editarTipoDocumento";
    }

    @PostMapping("/guardarEdicion")
    public String guardarEdicion(@ModelAttribute("TipDocs")TiposDocumentosDTO documentosDTO, BindingResult result, Model model){
        if (result.hasErrors()){
            List<Estados> estados = estadosService.lsitEstados();
            model.addAttribute("estados" , estados);
            return "admin/layauts/Parametros/tipoDocumento/editarTipoDocumento";
        }
        if (documentosDTO.getId() == null){
            throw new IllegalArgumentException("No se encontro el Id Seleccionado IdDocument");
        }
        documentosService.actualizarTipDocs(documentosDTO);
        return "redirect:/admin/tiposDocs?succesEdit";
    }


}
