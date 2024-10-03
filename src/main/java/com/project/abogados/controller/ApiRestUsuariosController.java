package com.project.abogados.controller;

import com.project.abogados.model.Usuarios;
import com.project.abogados.repository.UsuarioRepository;
import com.project.abogados.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/usuarios")
public class ApiRestUsuariosController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/registro")
    public ResponseEntity<String> registrarUser(@RequestBody Usuarios usuarios){
        try {
            usuarioService.registrarUsuario(usuarios);
            return new ResponseEntity<>("usuario registrado exitosamente", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Error al registrar el usuario" +e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Usuarios>> listarUsuarios(){
        List<Usuarios> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }


}
