package com.project.abogados.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class loginController {

    @GetMapping ("/login")
    public String showLoginPage() {
        return "login/login";
    }
}
