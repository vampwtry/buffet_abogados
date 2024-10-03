package com.project.abogados.services;

import com.project.abogados.model.Roles;
import com.project.abogados.model.Usuarios;
import com.project.abogados.repository.RolRepository;
import com.project.abogados.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;



@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private  RolRepository rolRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public void registrarUsuario(Usuarios usuarios) {
        Optional<Roles> rolUser = rolRepository.findByNombreRol("ROLE_USER");
        if (rolUser.isPresent()) {
            usuarios.setRol(rolUser.get());
            String Encript = passwordEncoder.encode(usuarios.getContraseña());
            usuarios.setContraseña(Encript);
            usuarioRepository.save(usuarios);
        } else {
            throw new RuntimeException("El rol -USER- no fue encontrado");
        }
    }


}
