package com.project.abogados.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String paginaAdminLogin() {
        return "admin/index";
    }

    @GetMapping("/admin/usuarios")
    public String listUsers(){
        return  "admin/layauts/usuarios";
    }
}


