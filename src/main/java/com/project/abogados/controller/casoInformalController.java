package com.project.abogados.controller;

import com.project.abogados.dtos.AbogadosDTO;
import com.project.abogados.dtos.CasosInformalesDTO;
import com.project.abogados.services.AbogadosService;
import com.project.abogados.services.CasosInformalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/casos")
public class casoInformalController {
    @Autowired
    private CasosInformalesService casosInformalesService;
    @Autowired
    private AbogadosService abogadosService;



}
